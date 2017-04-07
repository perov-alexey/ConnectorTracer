angular.module('tracer')

.controller('lightweightGenerator.controller', [
    '$scope', '$rootScope', '$http', '$httpParamSerializer',
        function($scope, $rootScope, $http, $httpParamSerializer) {

            $scope.connectorsAmount = 5;
            $scope.pinsAmount = 3;
            $scope.columnPinsAmount = 2;
            $scope.linksAmount = 10;
            $scope.requireBestSolution = true;
            $scope.isDebugEnabled = false;
            $scope.algorithmType = "BRANCH_AND_BOUND";
            $scope.minChannelCapacity = 4;
            $scope.maxChannelCapacity = 6;

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
                var queryString = $httpParamSerializer({
                    connectorsAmount: $scope.connectorsAmount,
                    linksAmount: $scope.linksAmount,
                    pinsAmount: $scope.pinsAmount,
                    columnPinsAmount: $scope.columnPinsAmount,
                    minChannelCapacity: $scope.minChannelCapacity,
                    maxChannelCapacity: $scope.maxChannelCapacity
                });
                $http.get("field?" + queryString).then(function(response) {
                    $scope.field = JSON.stringify(response.data);
                });
            }

        }
]);
