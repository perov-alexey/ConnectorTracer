angular.module('tracer')

.controller('viewer.controller', [
    '$scope','$rootScope', '$routeParams',
        function($scope, $rootScope, $routeParams) {

            console.dir($routeParams);

            //TODO To the drawer!
            drawConnectors = function() {
                var drawer = new Drawer();
                var drawerHelper = new DrawerHelper();

                var connectors = drawerHelper.getConnectors($rootScope.field);
                $.each(connectors, function(i, connector) {
                    drawer.drawConnector(connector.x, connector.y, connector.width, connector.height);
                    var pins = drawerHelper.getPins(connector);
                    $.each(pins, function(i, pin) {
                        pin = drawerHelper.getPinAbsolute($rootScope.field, pin);
                        drawer.drawPin(pin.x, pin.y);
                    });
                    var topChannel = drawerHelper.getChannel(connector, true);
                    var bottomChannel = drawerHelper.getChannel(connector, false);
                    drawer.drawChannel(connector.x, connector.y - drawer.CHANNEL_WIDTH, connector.width, topChannel.maxCapacity == topChannel.occupancy, topChannel.top, topChannel.occupancy + "/" + topChannel.maxCapacity);
                    drawer.drawChannel(connector.x, connector.y + connector.height, connector.width, bottomChannel.maxCapacity == bottomChannel.occupancy, bottomChannel.top, bottomChannel.occupancy + "/" + bottomChannel.maxCapacity);
                });

                var links = drawerHelper.getAbsoluteLinks($rootScope.field);
                $.each(links, function(i, link) {
                    drawer.drawLink(link.firstPin.x, link.firstPin.y, link.secondPin.x, link.secondPin.y);
                });

                var traces = drawerHelper.getTraces($rootScope.field);
                $.each(traces, function(i, trace) {
                    var points = drawerHelper.getTracePoints($rootScope.field, trace);
                    if (points) {
                        drawer.drawTrace(points);
                    }
                });
            }

            drawConnectors();

        }
]);
