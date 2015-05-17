'use strict';

var m = angular.module('gotowankoApp.showRecipeView', ['ngRoute', 'dateFilters'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/recipes/:recipeId', {
            templateUrl: '/recipes/show/show.html',
            controller: 'ShowRecipeController'
        });
    }]);

m.controller('ShowRecipeController', ['$scope', '$cookieStore', '$http', '$log', '$routeParams', '$location', function ($scope, $cookieStore, $http, $log, $routeParams, $location) {
    $scope.recipeId = $routeParams.recipeId;

    $http.get('/rest/recipes/' + $routeParams.recipeId)
        .success(function (data, status, headers, config) {
            $log.info(status + ": " + data);
            $scope.recipe = data;

            $scope.canEdit = function () {
                return $cookieStore.get('current.user').id == $scope.recipe.userId;
            }

            $scope.canDelete = function () {
                return $cookieStore.get('current.user').id == $scope.recipe.userId;
            }

            $scope.editRecipe = function () {
                if (!$scope.canEdit())
                    return;

                $location.path("/recipes/" + $scope.recipeId + "/edit")
            };

            $scope.editRecipeStep = function (stepNumber) {
                if (!$scope.canEdit())
                    return;

                $location.path("/recipes/" + $scope.recipeId + "/edit/" + stepNumber)
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
        })
        .error(function (data, status, headers, config) {
            $log.warn(status + ": " + data);
            $scope.setAlert({type: 'danger', msg: data.errorMessage});
        });
}]);
