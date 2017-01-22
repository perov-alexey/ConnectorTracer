angular.module('tracer')

.controller('viewer.controller', [
    '$scope','$rootScope', '$routeParams',
        function($scope, $rootScope) {

            //TODO To the drawer!
            drawConnectors = function(field) {
                var drawer = new Drawer();
                var drawerHelper = new DrawerHelper();

                drawer.clearField();

                var connectors = drawerHelper.getConnectors(field);
                $.each(connectors, function(i, connector) {
                    drawer.drawConnector(connector.x, connector.y, connector.width, connector.height);
                    var pins = drawerHelper.getPins(connector);
                    $.each(pins, function(i, pin) {
                        pin = drawerHelper.getPinAbsolute(field, pin);
                        drawer.drawPin(pin.x, pin.y);
                    });
                    var topChannel = drawerHelper.getChannel(connector, true);
                    var bottomChannel = drawerHelper.getChannel(connector, false);
                    drawer.drawChannel(connector.x, connector.y - drawer.CHANNEL_WIDTH, connector.width, topChannel.maxCapacity == topChannel.occupancy, topChannel.top, topChannel.occupancy + "/" + topChannel.maxCapacity);
                    drawer.drawChannel(connector.x, connector.y + connector.height, connector.width, bottomChannel.maxCapacity == bottomChannel.occupancy, bottomChannel.top, bottomChannel.occupancy + "/" + bottomChannel.maxCapacity);
                });

                var links = drawerHelper.getAbsoluteLinks(field);
                $.each(links, function(i, link) {
                    drawer.drawLink(link.firstPin.x, link.firstPin.y, link.secondPin.x, link.secondPin.y);
                });

                var traces = drawerHelper.getTraces(field);
                $.each(traces, function(i, trace) {
                    var points = drawerHelper.getTracePoints(field, trace);
                    if (points) {
                        drawer.drawTrace(points);
                    }
                });
            };

            function drawNextStep(steps) {
                if (steps.length > 0) {
                    drawConnectors(steps[0]);
                    setTimeout(drawNextStep.bind(null, steps), 1000);
                    steps.shift();
                }
            }

            drawNextStep($rootScope.steps);

        }
]);
