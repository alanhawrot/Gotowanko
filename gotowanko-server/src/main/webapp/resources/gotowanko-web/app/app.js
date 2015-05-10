'use strict';

// Declare app level module which depends on views, and components
angular.module('gotowankoApp', [
    'ngRoute',
    'gotowankoApp.searchView',
    'gotowankoApp.anotherView',
    'gotowankoApp.version'
]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/search'});
    }]);
