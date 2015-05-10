'use strict';

// Declare app level module which depends on views, and components
angular.module('gotowankoApp', [
    'ngRoute',
    'gotowankoApp.searchView',
    'gotowankoApp.loginView',
    'gotowankoApp.anotherView',
    'gotowankoApp.version'
]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/login'});
    }]);
