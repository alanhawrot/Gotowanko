'use strict';

var m = angular.module('gotowankoApp.showRecipeView', ['ngRoute', 'dateFilters'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/recipes/:recipeId', {
            templateUrl: '/recipes/show/show.html',
            controller: 'ShowRecipeController'
        });
    }]);

m.controller('ShowRecipeController', ['$scope', '$http', '$log', '$routeParams', '$route', '$location', '$cookieStore', function ($scope, $http, $log, $routeParams, $route, $location, $cookieStore) {
    $scope.recipeId = $routeParams.recipeId;

    $scope.canLike = function () {
        if ($scope.$parent.loggedUser === undefined) {
            return false;
        }

        var recipeId = parseInt($routeParams.recipeId);
        var index = $scope.$parent.loggedUser.recipeLikes.indexOf(recipeId);

        return index == -1;
    };

    $scope.canDislike = function () {
        if ($scope.$parent.loggedUser === undefined) {
            return false;
        }

        var recipeId = parseInt($routeParams.recipeId);
        var index = $scope.$parent.loggedUser.recipeLikes.indexOf(recipeId);

        return index != -1;
    };

    $scope.likeOrDislike = function (likeOrDislike) {
        var likeOrDislikeRecipeUrl = '/rest/recipes/' + $scope.recipeId + likeOrDislike;
            $http.get(likeOrDislikeRecipeUrl)
            .success(function () {
                $http.get('/rest/users/currently_logged')
                    .success(function (data, status, headers, config) {
                        $cookieStore.put('current.user', data);
                        $scope.$parent.loggedUser = data;
                        $log.info(status + ": " + data);
                        $route.reload();
                    })
                    .error(function (data, status, headers, config) {
                        $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                        if (status == 401) {
                            $scope.clearSession();
                            $location.path('/login');
                        }
                    });
            })
            .error(function (data, status) {
                $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                if (status == 401) {
                    $scope.clearSession();
                    $location.path('/login');
                }
            });
    };

    $scope.addComment = function () {
        var addCommentUrl = '/rest/recipes/' + $scope.recipeId + '/comments';
        $http.post(addCommentUrl, {'content': $scope.newComment})
            .success(function () {
                $route.reload();
            })
            .error(function () {
                $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                if (status == 401) {
                    $scope.clearSession();
                    $location.path('/login');
                }
            });
    };

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
