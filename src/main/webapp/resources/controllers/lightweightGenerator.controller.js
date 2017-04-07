angular.module('tracer')

.controller('lightweightGenerator.controller', [
    '$scope', '$rootScope', '$http', '$httpParamSerializer',
        function($scope, $rootScope, $http, $httpParamSerializer) {

            $scope.connectorsAmount = 100;
            $scope.pinsAmount = 12;
            $scope.pinsRowsAmount = 2;
            $scope.linksAmount = 200;
            $scope.requireBestSolution = true;
            $scope.isDebugEnabled = false;
            $scope.algorithmType = "BRANCH_AND_BOUND";

            $scope.traceField = function() {
                var queryString = $httpParamSerializer({
                    debugEnabled: $scope.isDebugEnabled,
                    requireBestSolution: $scope.requireBestSolution,
                    algorithmType: $scope.algorithmType
                });
                $http.post("traced?" + queryString, $scope.field).then(function(response) {
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
