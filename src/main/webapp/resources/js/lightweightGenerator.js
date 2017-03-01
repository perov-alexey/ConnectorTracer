function LightweightGenerator() {

    var MIN_CONNECTOR_Y = 100;
    var MAX_CONNECTOR_Y = 100;

    //In one row so far
    var MIN_CONNECTOR_HEIGHT = 200;
    var MAX_CONNECTOR_HEIGHT = 200;

    var MIN_CONNECTOR_WIDTH = 50;
    var MAX_CONNECTOR_WIDTH = 150;

    var MIN_CONNECTOR_PADDING = 30;
    var MAX_CONNECTOR_PADDING = 50;

    var MIN_CHANNEL_CAPACITY = 50;
    var MAX_CHANNEL_CAPACITY = 100;

    var FIRST_CONNECTOR_PADDING = 10;

    var CANVAS_WIDTH = 200000;

    LightweightGenerator.prototype.generateField = function(connectorsAmount, pinsAmount, pinsRowsAmount, linksAmount) {
        var generator = this;
        var field = {
            connectors: [],
            links: []
        };
        for (var i = 0; i < connectorsAmount; i++) {
            if (field.connectors.length > 0) {
                var lastConnector = field.connectors[field.connectors.length - 1];
                if (lastConnector.x + MAX_CONNECTOR_WIDTH + MAX_CONNECTOR_PADDING < CANVAS_WIDTH) {
                    field.connectors.push(generator.generateConnector(lastConnector, pinsAmount, pinsRowsAmount));
                }
            } else {
                field.connectors.push(generator.generateConnector(null, pinsAmount, pinsRowsAmount));
            }
        }
        for (var j = 0; j < linksAmount; j++) {
            field.links.push(generator.generateLink(field.connectors));
        }
        return field;
    };

    LightweightGenerator.prototype.generateConnector = function(lastConnector, pinsAmount, pinsRowsAmount) {
        var generator = this;
        var connectorPadding = FIRST_CONNECTOR_PADDING;

        if (lastConnector) {
            connectorPadding = lastConnector.x + lastConnector.width + generator._between(MIN_CONNECTOR_PADDING, MAX_CONNECTOR_PADDING);
        }

        var connector = {
            x: connectorPadding,
            y: generator._between(MIN_CONNECTOR_Y, MAX_CONNECTOR_Y),
            height: generator._between(MIN_CONNECTOR_HEIGHT, MAX_CONNECTOR_HEIGHT),
            width: generator._between(MIN_CONNECTOR_WIDTH, MAX_CONNECTOR_WIDTH),
            pins: []
        };

        for (var i = 0; i < pinsAmount; i++) {
            connector.pins.push(generator.generatePin(connector, pinsRowsAmount));
        }
        connector.topChannel = generator.generateChannel(connector, true);
        connector.bottomChannel = generator.generateChannel(connector, false);

        return connector;
    };

    LightweightGenerator.prototype.generatePin = function(connector, pinsRowsAmount) {
        var generator = this;
        return {
            x: generator._between(1, pinsRowsAmount) * (connector.width / (pinsRowsAmount + 1)),
            y: generator._between(0, connector.height),
            container: connector.x + "_" + connector.y
        };
    };

    LightweightGenerator.prototype.generateChannel = function(connector, top) {
        var generator = this;
        return {
            occupancy: 0,
            maxCapacity: generator._between(MIN_CHANNEL_CAPACITY, MAX_CHANNEL_CAPACITY),
            connector: connector.x + "_" + connector.y,
            top: top
        };
    };

    LightweightGenerator.prototype.generateLink = function(connectors) {
        var generator = this;
        var link = {};
        var firstConnectorNumber = generator._between(0, connectors.length - 1);
        var secondConnectorNumber = generator._between(0, connectors.length - 1);
        link.firstPin = connectors[firstConnectorNumber].pins[generator._between(0, connectors[firstConnectorNumber].pins.length - 1)];
        link.secondPin = connectors[secondConnectorNumber].pins[generator._between(0, connectors[secondConnectorNumber].pins.length - 1)];
        return link;
    };

    LightweightGenerator.prototype._between = function(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

}
