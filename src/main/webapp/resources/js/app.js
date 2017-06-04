angular.module('tracer', ['ngRoute', 'ngMaterial', 'ngSanitize']).config([
        '$routeProvider', '$mdThemingProvider',
        function($routeProvider, $mdThemingProvider){
            $mdThemingProvider.theme('default')
                .primaryPalette('cyan', {
                    'default': '500',
                })
                .accentPalette('cyan', {
                    'default': '200'
                })
                .backgroundPalette('grey', {
                    'default': '200',
                });

            $routeProvider
                .when('/inputs',{
                    templateUrl: 'templates/inputs.html',
                    controller: 'inputs.controller'
                })
                .when('/tracer',{
                    templateUrl: 'templates/lightweightGenerator.html',
                    controller: 'lightweightGenerator.controller'
                })
                .when('/viewer',{
                    templateUrl: 'templates/viewer.html',
                    controller: 'viewer.controller'
                })
                .otherwise({
                    redirectTo: '/inputs'
                });
        }
    ]);