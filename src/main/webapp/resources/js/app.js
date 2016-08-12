angular.module('tracer', ['ngRoute']).config([
        '$routeProvider',
        function($routeProvider, $locationProvider){
            $routeProvider
                .when('/tracer',{
                    templateUrl: 'templates/lightweightGenerator.html',
                    controller: 'lightweightGenerator.controller'
                })
                .otherwise({
                    redirectTo: '/tracer'
                });
        }
    ]);