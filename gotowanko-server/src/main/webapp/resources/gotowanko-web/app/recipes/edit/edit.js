'use strict';

var m = angular.module('gotowankoApp.editRecipeView', ['ngRoute', 'dateFilters'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/recipes/:recipeId/edit', {
            templateUrl: '/recipes/edit/edit.html',
            controller: 'EditRecipeController'
        });
    }]);

m.controller('EditRecipeController', ['$scope', '$http', '$log', '$routeParams', '$location', function ($scope, $http, $log, $routeParams, $location) {
    $scope.recipeId = $routeParams.recipeId;

    $scope.canEdit = function () {
        return true;
    }

    $scope.canDelete = function () {
        return true;
    }

    $scope.editRecipe = function () {
        if (!$scope.canEdit())
            return;

        $location.path("/recipes/" + $scope.recipeId + "/edit")
    };

    $scope.deleteRecipe = function () {
        if (!$scope.canDelete())
            return;

        $http.delete('/rest/recipes/' + $routeParams.recipeId)
            .success(function (data, status, headers, config) {
                $log.info(status + ": " + data);
                $scope.recipe = data;
                $scope.setAlert({type: 'success', msg: "Recipe removed successfully"});
                $location.path("/");
            })
            .error(function (data, status, headers, config) {
                $log.warn(status + ": " + data);
                $scope.setAlert({type: 'danger', msg: data.errorMessage});
            });
    };

    $scope.editRecipeStep = function (stepNumber) {
        if (!$scope.canEdit())
            return;

        $location.path("/recipes/" + $scope.recipeId + "/edit/" + stepNumber)
    };

    $http.get('/rest/recipes/' + $routeParams.recipeId)
        .success(function (data, status, headers, config) {
            $log.info(status + ": " + data);
            $scope.recipe = data;
        })
        .error(function (data, status, headers, config) {
            $log.warn(status + ": " + data);
            $scope.setAlert({type: 'danger', msg: data.errorMessage});
        });
}]);
