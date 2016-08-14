angular.module('tracer', ['ngRoute']).config([
        '$routeProvider',
        function($routeProvider){
            $routeProvider
                .when('/tracer',{
                    templateUrl: 'templates/lightweightGenerator.html',
                    controller: 'lightweightGenerator.controller'
                })
                .when('/viewer',{
                    templateUrl: 'templates/viewer.html',
                    controller: 'viewer.controller'
                })
                .otherwise({
                    redirectTo: '/tracer'
                });
        }
    ]);