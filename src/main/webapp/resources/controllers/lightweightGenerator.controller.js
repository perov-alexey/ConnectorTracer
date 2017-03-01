angular.module('tracer')

.controller('lightweightGenerator.controller', [
    '$scope', '$rootScope', '$http',
        function($scope, $rootScope, $http) {

            $scope.connectorsAmount = 100;
            $scope.pinsAmount = 12;
            $scope.pinsRowsAmount = 2;
            $scope.linksAmount = 200;

            $scope.traceField = function() {
                $http.post("field", $scope.field).then(function(response) {
                    $rootScope.steps = response.data;
                    location.hash = "#/viewer";
                });
            };

            $scope.traceFieldWithDebug = function() {
                $http.post("field?debugEnabled=true", $scope.field).then(function(response) {
                    $rootScope.steps = response.data;
                    location.hash = "#/viewer";
                });
            };

            $scope.generateField = function() {
                $scope.field = JSON.stringify(new LightweightGenerator().generateField($scope.connectorsAmount, $scope.pinsAmount,
                    $scope.pinsRowsAmount, $scope.linksAmount));
            }

        }
]);
