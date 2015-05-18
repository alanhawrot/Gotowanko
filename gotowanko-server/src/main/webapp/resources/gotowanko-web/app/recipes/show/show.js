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

    $http.get('/rest/recipes/' + $routeParams.recipeId)
        .success(function (data, status, headers, config) {
            $log.info(status + ": " + data);
            $scope.recipe = data;

            $scope.canEditComment = function (commentId) {
                if ($scope.$parent.loggedUser === undefined) {
                    return false;
                }

                var comments = $scope.$parent.loggedUser.comments;

                for (var i = 0; i < comments.length; i++) {
                    if (comments[i].id == commentId) {
                        return true;
                    }
                }

                return false;
            };

            $scope.editComment = function (comment) {
                var editCommentUrl = '/rest/recipes/' + $routeParams.recipeId + '/comments/' + comment.id;
                $http.put(editCommentUrl, {'content': comment.content})
                    .success(function () {
                        $http.get('/rest/users/currently_logged')
                            .success(function (data, status, headers, config) {
                                $cookieStore.put('current.user', data);
                                $scope.$parent.loggedUser = data;
                                $route.reload();
                            })
                            .error(function (data, status, headers, config) {
                                if (status == 401) {
                                    $scope.clearSession();
                                    $location.path('/login');
                                } else {
                                    $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                                }
                            });
                    })
                    .error(function (data, status) {
                        if (status == 401) {
                            $scope.clearSession();
                            $location.path('/login');
                        } else {
                            $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                        }
                    });
            };

            $scope.deleteComment = function (comment) {
                var deleteCommentUrl = '/rest/recipes/' + $routeParams.recipeId + '/comments/' + comment.id;
                $http.delete(deleteCommentUrl)
                    .success(function () {
                        $http.get('/rest/users/currently_logged')
                            .success(function (data, status, headers, config) {
                                $cookieStore.put('current.user', data);
                                $scope.$parent.loggedUser = data;
                                $route.reload();
                            })
                            .error(function (data, status, headers, config) {
                                if (status == 401) {
                                    $scope.clearSession();
                                    $location.path('/login');
                                } else {
                                    $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                                }
                            });
                    })
                    .error(function (data, status) {
                        if (status == 401) {
                            $scope.clearSession();
                            $location.path('/login');
                        } else {
                            $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                        }
                    });
            };

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
                                if (status == 401) {
                                    $scope.clearSession();
                                    $location.path('/login');
                                } else {
                                    $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                                }
                            });
                    })
                    .error(function (data, status) {
                        if (status == 401) {
                            $scope.clearSession();
                            $location.path('/login');
                        } else {
                            $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                        }
                    });
            };

            $scope.addComment = function () {
                var addCommentUrl = '/rest/recipes/' + $scope.recipeId + '/comments';
                $http.post(addCommentUrl, {'content': $scope.newComment})
                    .success(function () {
                        $http.get('/rest/users/currently_logged')
                            .success(function (data, status, headers, config) {
                                $cookieStore.put('current.user', data);
                                $scope.$parent.loggedUser = data;
                                $log.info(status + ": " + data);
                                $route.reload();
                            })
                            .error(function (data, status, headers, config) {
                                if (status == 401) {
                                    $scope.clearSession();
                                    $location.path('/login');
                                } else {
                                    $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                                }
                            });
                    })
                    .error(function (data, status) {
                        if (status == 401) {
                            $scope.clearSession();
                            $location.path('/login');
                        } else {
                            $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                        }
                    });
            };

            $scope.canEdit = function () {
                return $cookieStore.get('current.user').id == $scope.recipe.userId;
            };

            $scope.canDelete = function () {
                return $cookieStore.get('current.user').id == $scope.recipe.userId;
            };

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
                        if (status == 401) {
                            $scope.clearSession();
                            $location.path('/login');
                        } else {
                            $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                        }
                    });
            };

            $scope.editRecipeStep = function (stepNumber) {
                if (!$scope.canEdit())
                    return;

                $location.path("/recipes/" + $scope.recipeId + "/edit/" + stepNumber)
            };
        })
        .error(function (data, status, headers, config) {
            $log.warn(status + ": " + data);
            $scope.setAlert({type: 'danger', msg: data.errorMessage});
        });
}]);
