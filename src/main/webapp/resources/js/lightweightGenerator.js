function LightweightGenerator() {

    var AMOUNT_OF_CONNECTORS = 6;
    var AMOUNT_OF_PINS = 6;
    var AMOUNT_OF_LINKS = 6;

    //TODO in different rows
    //In one row so far
    var MIN_CONNECTOR_Y = 300;
    var MAX_CONNECTOR_Y = 300;

    //In one row so far
    var MIN_CONNECTOR_HEIGHT = 200;
    var MAX_CONNECTOR_HEIGHT = 200;

    var MIN_CONNECTOR_WIDTH = 50;
    var MAX_CONNECTOR_WIDTH = 150;

    var MIN_CONNECTOR_PADDING = 30;
    var MAX_CONNECTOR_PADDING = 50;

    var MAX_CHANNEL_CAPACITY = 3;

    var CANVAS_WIDTH = 1300;



    LightweightGenerator.prototype.generateField = function() {
        var field = {
            connectors: [],
            links: []
        };
        for (var i = 0; i < AMOUNT_OF_CONNECTORS; i++) {
            if (field.connectors.length > 0) {
                var lastConnector = field.connectors[field.connectors.length - 1];
                if (lastConnector.x + MAX_CONNECTOR_WIDTH + MAX_CONNECTOR_PADDING < CANVAS_WIDTH) {
                    field.connectors.push(this.generateConnector(lastConnector));
                }
            } else {
                field.connectors.push(this.generateConnector());
            }
        }
        for (var j = 0; j < AMOUNT_OF_LINKS; j++) {
            field.links.push(this.generateLink(field.connectors));
        }
        return field;
    };

    LightweightGenerator.prototype.generateConnector = function(lastConnector) {
        var x = 10;

        if (lastConnector) {
            x = lastConnector.x + lastConnector.width + this._between(MIN_CONNECTOR_PADDING, MAX_CONNECTOR_PADDING);
        }

        var connector = {
            x: x,
            y: this._between(MIN_CONNECTOR_Y, MAX_CONNECTOR_Y),
            height: this._between(MIN_CONNECTOR_HEIGHT, MAX_CONNECTOR_HEIGHT),
            width: this._between(MIN_CONNECTOR_WIDTH, MAX_CONNECTOR_WIDTH),
            pins: []
        }

        for (var i = 0; i < AMOUNT_OF_PINS; i++) {
            connector.pins.push(this.generatePin(connector));
        }
        connector.topChannel = this.generateChannel(connector, true);
        connector.bottomChannel = this.generateChannel(connector, false);

        return connector;
    }

    LightweightGenerator.prototype.generatePin = function(connector) {
        var pin = {
            x: this._between(0, connector.width),
            y: this._between(0, connector.height),
            container: connector.x + "_" + connector.y
        }
        return pin;
    }

    LightweightGenerator.prototype.generateChannel = function(connector, top) {
        var channel = {
            occupancy: 0,
            maxCapacity: this._between(0, MAX_CHANNEL_CAPACITY),
            connector: connector.x + "_" + connector.y,
            top: top
        }
        return channel;
    }

    LightweightGenerator.prototype.generateLink = function(connectors) {
        var link = {};
        var firstConnectorNumber = this._between(0, connectors.length - 1);
        var secondConnectorNumber = this._between(0, connectors.length - 1);
        link.firstPin = connectors[firstConnectorNumber].pins[this._between(0, connectors[firstConnectorNumber].pins.length - 1)];
        link.secondPin = connectors[secondConnectorNumber].pins[this._between(0, connectors[secondConnectorNumber].pins.length - 1)];
        return link;
    }

    LightweightGenerator.prototype._between = function(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

}
