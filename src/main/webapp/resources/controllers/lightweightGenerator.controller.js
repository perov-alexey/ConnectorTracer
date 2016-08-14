angular.module('tracer')

.controller('lightweightGenerator.controller', [
    '$scope', '$rootScope', '$http',
        function($scope, $rootScope, $http) {

            $scope.field = new LightweightGenerator().generateField();

            $scope.traceField = function() {
                $http.post("field", $scope.field).then(function(response) {
                    $rootScope.field = response.data;
                    location.hash = "#/viewer";
                });
            }

        }
]);
