angular.module('tracer')

.controller("lightweightGenerator.controller", [
    "$scope",
        function($scope) {

            $scope.field = new LightweightGenerator().generateField();

            $scope.traceField = function() {
                alert($scope.field);
            }

        }
]);
