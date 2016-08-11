angular.module('tracer', ['ngRoute']);

angular.module('tracer').config([
        '$routeProvider',
        function($routeProvider, $locationProvider){
            $routeProvider
                .when('/settings',{
                    templateUrl: 'lightweightGenerator.html',
                    controller: 'settings.controller',
                    reloadOnSearch: false,
                    pageNumber: '21'
                })
                .otherwise({
                    redirectTo: '/settings'
                });
        }
    ]);