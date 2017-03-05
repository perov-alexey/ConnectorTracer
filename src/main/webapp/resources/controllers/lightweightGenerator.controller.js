angular.module('tracer')

.controller('lightweightGenerator.controller', [
    '$scope', '$rootScope', '$http', '$httpParamSerializer',
        function($scope, $rootScope, $http, $httpParamSerializer) {

            $scope.connectorsAmount = 100;
            $scope.pinsAmount = 12;
            $scope.pinsRowsAmount = 2;
            $scope.linksAmount = 200;
            $scope.requireBestSolution = true;

            $scope.traceField = function() {
                var queryString = $httpParamSerializer({
                    debugEnabled: false,
                    requireBestSolution: $scope.requireBestSolution
                });
                $http.post("field?" + queryString, $scope.field).then(function(response) {
                    $rootScope.steps = response.data;
                    location.hash = "#/viewer";
                });
            };

            $scope.traceFieldWithDebug = function() {
                var queryString = $httpParamSerializer({
                    debugEnabled: true,
                    requireBestSolution: $scope.requireBestSolution
                });
                $http.post("field?" + queryString, $scope.field).then(function(response) {
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
