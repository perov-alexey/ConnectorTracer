//TODO Enable use strict
//"use strict";

function DrawerHelper() {

    var INDENT_CONTAINER_VERTICAL_EDGE = 10;
    var INDENT_CONTAINER_HORIZONTAL_EDGE = 10;

    DrawerHelper.prototype.getConnectors = function(config) {
        return config.connectors || [];
    };

    DrawerHelper.prototype.getPins = function(connector) {
        return connector.pins || [];
    };

    DrawerHelper.prototype.getPinAbsolute = function(config, pin) {
        var newPin = {};
        $.extend(true, newPin, pin);
        newPin.x = pin.x + this.getConnectorByID(config, pin.container).x;
        newPin.y = pin.y + this.getConnectorByID(config, pin.container).y;
        return newPin;
    };

    DrawerHelper.prototype.getChannel = function(connector, isTop) {
        return isTop ? connector.topChannel : connector.bottomChannel;
    };

    DrawerHelper.prototype.getRelativeLinks = function(config) {
        return config.links || [];
    };

    DrawerHelper.prototype.getAbsoluteLinks = function(config) {
        var self = this;
        var links = [];
        $.each(config.links, function(i, link) {
            var newLink = {};
            $.extend(true, newLink, link);
            var firstConnector = self.getConnectorByID(config, link.firstPin.container);
            var secondConnector = self.getConnectorByID(config, link.secondPin.container);

            if (firstConnector) {
                newLink.firstPin.x = link.firstPin.x + firstConnector.x;
                newLink.firstPin.y = link.firstPin.y + firstConnector.y;
            } else {
                throw new Error("Configuration error - missing connector");
            }

            if (secondConnector) {
                newLink.secondPin.x = link.secondPin.x + secondConnector.x;
                newLink.secondPin.y = link.secondPin.y + secondConnector.y;
            } else {
                throw new Error("Configuration error - missing connector");
            }
            links.push(newLink);
        });
        return links;
    };

    DrawerHelper.prototype.getTraces = function(config) {
        return config.traces || [];
    };

    DrawerHelper.prototype.getConnectorByID = function(config, id) {
        var connectors = this.getConnectors(config);
        var selectedConnectors = $(connectors).filter(function(index) {
            return connectors[index]['@id'] === id;
        });
        return selectedConnectors[0];
    };

    DrawerHelper.prototype.getTracePoints = function(config, trace){
        if (trace.path.length > 0) {
            //TODO Try to solve collisions, maybe needs additional layer for it.
            var points = [];

            var firstAbsolutePin = this.getPinAbsolute(config, trace.link.firstPin);
            var secondAbsolutePin = this.getPinAbsolute(config, trace.link.secondPin);
            var firstConnector = this.getConnectorByID(config, trace.link.firstPin.container);
            var secondConnector = this.getConnectorByID(config, trace.link.secondPin.container);

            var sparePartY;
            if (trace.path[0].top) {
                sparePartY = firstConnector.y - INDENT_CONTAINER_HORIZONTAL_EDGE;
            } else {
                //TODO write test for bottom trace
                sparePartY = firstConnector.y + firstConnector.height + INDENT_CONTAINER_HORIZONTAL_EDGE
            }

            points.push({
                x: firstAbsolutePin.x,
                y: firstAbsolutePin.y
            });

            points.push({
                x: firstConnector.x + firstConnector.width + INDENT_CONTAINER_VERTICAL_EDGE,
                y: firstAbsolutePin.y
            });

            points.push({
                x: firstConnector.x + firstConnector.width + INDENT_CONTAINER_VERTICAL_EDGE,
                y: sparePartY
            });

            points.push({
                x: secondConnector.x - INDENT_CONTAINER_VERTICAL_EDGE,
                y: sparePartY
            });

            points.push({
                x: secondConnector.x - INDENT_CONTAINER_VERTICAL_EDGE,
                y: secondAbsolutePin.y
            });

            points.push({
                x: secondAbsolutePin.x,
                y: secondAbsolutePin.y
            });

            return points;
        }
    }
}
