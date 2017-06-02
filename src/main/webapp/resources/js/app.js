angular.module('tracer', ['ngRoute', 'ngMaterial']).config([
        '$routeProvider', '$mdThemingProvider',
        function($routeProvider, $mdThemingProvider){
            $mdThemingProvider.theme('default')
                .primaryPalette('cyan', {
                    'default': '500',
                    'hue-1': '50'
                })
                .accentPalette('cyan', {
                    'default': '200'
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
                    redirectTo: '/tracer'
                });
        }
    ]);