angular.module('tracer')

.controller('inputs.controller', [
    "$scope", "$mdDialog", function($scope, $mdDialog) {

        $scope.algorithms = [
            { title: "Метод полного перебора", name: "BRUTE_FORCE" },
            { title: "Метод ветвей и границ", name: "BRANCH_AND_BOUND" },
            { title: "Метод динамического программирования", name: "DYNAMIC_PROGRAMMING" }
        ];

        $scope.addConnector = function() {
            $scope.connectors.push({
                width: 100,
                pinsAmount: 6
            });
        };

        $scope.addLink = function() {
            $scope.links.push({});
        };

        //Init connectors and links
        $scope.connectors = [];
        $scope.links = [];
        $scope.addConnector();
        $scope.addLink();

        $scope.getRange = function(maxValue) {
            if (maxValue !== undefined) {
                return Array.from(Array(maxValue).keys());
            } else {
                return [];
            }
        };

        $scope.showConnectorDeleteConfirm = function(index) {
            var confirm = $mdDialog.confirm()
                  .title('Вы точно хотите удалить этот разъем?')
                  .textContent('Операцию удаления нельзя отменить')
                  .ok('Да')
                  .cancel('Нет');

            $mdDialog.show(confirm).then(function() {
                $scope.connectors.splice(index, 1);
            });
        };

        $scope.showLinkDeleteConfirm = function(index) {
            var confirm = $mdDialog.confirm()
                  .title('Вы точно хотите удалить эту связь?')
                  .textContent('Операцию удаления нельзя отменить')
                  .ok('Да')
                  .cancel('Нет');

            $mdDialog.show(confirm).then(function() {
                $scope.links.splice(index, 1);
            });
        };

        $scope.showAdviceMethodConfirm = function(index) {
            var confirm = $mdDialog.confirm()
                  .title('Выбор алгоритма решения')
                  .htmlContent('В зависимости от исходных условий задачи необходимо правильно выбрать метод ее решения. <br/>Желаете воспользоваться автоопределением подходящего метода?')
                  .ok('Да')
                  .cancel('Нет');

            $mdDialog.show(confirm).then(function() {
                $scope.adviceMethod();
            });
        };

        $scope.adviceMethod = function() {
            var field = $scope.generateField();
            var totalOccupancy = 0;
            var totalCapacity = 0;
            field.links.forEach(function(link) {
                totalOccupancy += link.occupancy;
            });

            field.connectors.forEach(function(connector) {
                totalCapacity += connector.topChannel.capacity + connector.bottomChannel.capacity;
            });

            var strengthCoefficient = totalOccupancy / (totalCapacity / field.connectors.length);
            if (strengthCoefficient > 1) {

            }
        };

        $scope.traceField = function() {
            $scope.field = $scope.generateJSON();
        };

        $scope.generateField = function() {
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
                    };

                    connector.pins.push(pin);
                }

                connector.topChannel = {
                    container: connector.index,
                    capacity: rawConnector.topChannelCapacity,
                    isTop: true
                };

                connector.bottomChannel = {
                    container: connector.index,
                    capacity: rawConnector.bottomChannelCapacity,
                    isTop: false
                };

                field.connectors.push(connector);
            });

            $scope.links.forEach(function(rawLink, index) {
                var link = {
                    occupancy: rawLink.occupancy,
                    firstPin: field.connectors[rawLink.firstConnector].pins[rawLink.firstPin],
                    secondPin: field.connectors[rawLink.secondConnector].pins[rawLink.secondPin]
                };

                field.links.push(link);
            });
            
            return field;
        }
        
    }
]);