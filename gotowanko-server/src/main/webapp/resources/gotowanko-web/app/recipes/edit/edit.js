'use strict';

var m = angular.module('gotowankoApp.editRecipeView', ['ngRoute', 'dateFilters'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/recipes/:recipeId/edit/:stepNumber?', {
            templateUrl: '/recipes/edit/edit.html',
            controller: 'EditRecipeController'
        });
    }]);

m.controller('EditRecipeController', ['$scope', '$cookieStore', '$http', '$log', '$routeParams', '$location',
    function ($scope, $cookieStore, $http, $log, $routeParams, $location) {
        $scope.recipeId = $routeParams.recipeId;

        $('#popover').popover({
            html: true,
            container: 'body',
            title: function () {
                return $("#popover-head").html();
            },
            content: function () {
                return $("#popover-content").html();
            }
        });

        $scope.closePopover = function () {
            $log.info("got click");
            $('[data-toggle="popover"]').each(function () {
                $log.info("got popover")
                $(this).popover('hide');
            });
        };


/*        if ($routeParams.stepNumber)
            $('#carousel-example-generic').carousel($routeParams.stepNumber);*/

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

                    $location.path("/recipes/" + $scope.recipeId + "/edit/")
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

m.controller('RealizationTimeController', function ($scope, $log) {
    var d = new Date();
    d.setHours(1);
    d.setMinutes(0);
    $scope.mytime = d;

    $scope.changed = function () {
        $log.log('Time changed to: ' + $scope.mytime);
    };
});