'use strict';

// Declare app level module which depends on views, and components
angular.module('gotowankoApp', [
  'ngRoute',
  'gotowankoApp.searchView',
  'myApp.version'
]).
config(['$routeProvider', function($routeProvider) {
  $routeProvider.otherwise({redirectTo: '/search'});
}]);
