'use strict';

var m = angular.module('gotowankoApp.showRecipeView', ['ngRoute', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/recipes/:recipeId', {
            templateUrl: '/recipes/show/show.html',
            controller: 'ShowRecipeController'
        });
    }]);

m.controller('ShowRecipeController', ['$scope', '$http', '$log', '$routeParams', function ($scope, $http, $log, $routeParams) {
    $http.get('/rest/recipes/' + $routeParams.recipeId)
        .success(function (data, status, headers, config) {
            $log.info(status + ": " + data);
            $scope.recipe = data;
        })
        .error(function (data, status, headers, config) {
            $log.warn(status + ": " + data);
            $scope.$parent.alerts = [];
            $scope.$parent.alerts.push({type: 'danger', msg: data.errorMessage});
        });
}]);
