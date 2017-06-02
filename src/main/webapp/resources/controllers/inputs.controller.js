angular.module('tracer')

.controller('inputs.controller', [
    "$scope", function($scope) {
        
        $scope.connectors = [];
        
        $scope.addConnector = function() {
            $scope.connectors.push({
                width: 100,
                pinsAmount: 6
            });
        };
        
        $scope.traceField = function() {
            $scope.field = $scope.generateJSON();
        };
        
        $scope.generateJSON = function() {
            var field = {
                connectors: [],
                links: []
            };
            
            $scope.connectors.forEach(function (rawConnector, index) {
                var connector = {
                    id: index,
                    width: rawConnector.width,
                    height: 200,
                    x: rawConnector.x,
                    y: 100,
                    pins: []
                };
                
                for (var i = 1; i < rawConnector.pinsAmount; i++) {
                    var lineNumber = (rawConnector.pinsAmount / 2) - i > 0 ? 1 : 2; 
                    var rowsNumber = Math.ceil(rawConnector.pinsAmount / 2);
                    
                    var pin = {
                        container: connector.index,
                        x: (connector.width / 3) * lineNumber,
                        y: (connector.height / (rowsNumber + 1)) * ((rawConnector.pinsAmount % i) + 1)
                    }
                    
                    connector.pins.push(pin);
                }
                
                connector.topChannel = {
                    container: connector.index,
                    capacity: rawConnector.topChannelCapacity,
                    isTop: true
                }
                
                connector.bottomChannel = {
                    container: connector.index,
                    capacity: rawConnector.bottomChannelCapacity,
                    isTop: false
                }
                
                field.connectors.push(connector);
            });
            
            return JSON.stringify(field);
        }
        
    }
]);