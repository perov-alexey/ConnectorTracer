angular.module('tracer')

.controller("lightweightGenerator.controller", [
    "$scope",
        function($scope) {

            $scope.field = new LightweightGenerator().generateField();

            $scope.traceField = function() {
                $.ajax({
                    "url": "field",
                    "data": JSON.stringify($scope.field),
                    "method": "POST",
                    'dataType': 'application/json',
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                }).fail(function() {
                    alert("fail!");
                }).
                done(function(data) {
                    $scope.field = JSON.parse(data.responseText);
                })
            }

        }
]);
