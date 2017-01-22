angular.module('tracer')

.controller('lightweightGenerator.controller', [
    '$scope', '$rootScope', '$http',
        function($scope, $rootScope, $http) {

            $scope.field = JSON.stringify(new LightweightGenerator().generateField());

            $scope.traceField = function() {
                $http.post("field", $scope.field).then(function(response) {
                    $rootScope.steps = response.data;
                    location.hash = "#/viewer";
                });
            }

        }
]);
