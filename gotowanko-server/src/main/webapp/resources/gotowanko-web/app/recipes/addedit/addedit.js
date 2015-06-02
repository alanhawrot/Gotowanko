'use strict';

var m = angular.module('gotowankoApp.createRecipeView', ['ngRoute']);

m.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/recipes_create', {
            templateUrl: '/recipes/addedit/addedit.html',
            controller: 'AddEditRecipeController'
        }).
        when('/recipes/:recipeId/edit/:recipeStepNumber?', {
            templateUrl: '/recipes/addedit/addedit.html',
            controller: 'AddEditRecipeController'
        });
}]);

m.controller('AddEditRecipeController', ['$scope', '$http', '$log', '$location', '$routeParams', '$timeout', '$window', function ($scope, $http, $log, $location, $routeParams, $timeout, $window) {
    $scope.form = {};

    $http.get('/rest/ingredients/list')
        .success(function (responseData) {
            $log.info("/rest/ingredients/list " + angular.toJson(responseData, true));
            $scope.ingredients = responseData;

            $scope.ingredientName = function (ingredientId) {
                for (var i = 0; i < $scope.ingredients.length; i++) {
                    if ($scope.ingredients[i].id == ingredientId)
                        return $scope.ingredients[i].name;
                }
            };

            $scope.ingredientIconUrl = function (ingredientId) {
                for (var i = 0; i < $scope.ingredients.length; i++) {
                    if ($scope.ingredients[i].id == ingredientId)
                        return $scope.ingredients[i].iconUrl;
                }
            };
        }).error(function (responseData, status) {
            $log.warn("responseData " + responseData);
            if (status == 401) {
                $scope.clearSession();
                $location.path('/login');
            } else {
                $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
            }
        });

    $http.get('/rest/ingredients/units')
        .success(function (responseData) {
            $log.info("/rest/ingredients/units " + angular.toJson(responseData, true));
            $scope.ingredientUnits = responseData.ingredientUnits;
        }).error(function (responseData, status) {
            $log.warn("responseData " + responseData);
            if (status == 401) {
                $scope.clearSession();
                $location.path('/login');
            } else {
                $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
            }
        });

    // Tworzony przez użytkownika Request Body
    $scope.recipe = {
        title: '',
        recipeSteps: [
            {
                stepNumber: 1, title: '', description: '', ingredients: []
            }
        ]
    };

    var recipeId = $routeParams.recipeId;
    if (recipeId !== undefined) {
        $http.get('/rest/recipes/' + recipeId)
            .success(function (data) {
                $log.info(angular.toJson(data, true));

                var recipe = $scope.recipe;

                recipe.title = data.title;
                recipe.approximateCost = data.approximateCost;
                recipe.photoUrl = data.photoUrl;
                $scope.tabs[0].cookingTime = data.cookingTime / 1000 / 60; // ms to minutes

                recipe.recipeSteps = [];
                for (var i = 0; i < data.recipeSteps.length; i++) {
                    var recipeStepIngredients = [];
                    for (var j = 0; j < data.recipeSteps[i].ingredients.length; j++) {
                        recipeStepIngredients.push(
                            {
                                ingredientId: data.recipeSteps[i].ingredients[j].id,
                                ingredientUnitId: data.recipeSteps[i].ingredients[j].unitId,
                                ingredientAmount: data.recipeSteps[i].ingredients[j].amount
                            }
                        );
                    }
                    $scope.tabs[i + 1] = {
                        timerDuration: data.recipeSteps[i].timerDuration / 1000, // ms to seconds
                        realizationTime: data.recipeSteps[i].realizationTime / 1000 / 60, // ms to minutes
                        active: false
                    };
                    recipe.recipeSteps.push(
                        {
                            stepNumber: i + 1,
                            title: data.recipeSteps[i].title,
                            photoUrl: data.recipeSteps[i].photoUrl,
                            videoUrl: data.recipeSteps[i].videoUrl,
                            description: data.recipeSteps[i].description,
                            ingredients: recipeStepIngredients
                        }
                    );
                }

                var stepNumber = $routeParams.recipeStepNumber;
                if (stepNumber && stepNumber >= 1 && stepNumber <= recipe.recipeSteps.length) {
                    setAllTabsInactive();
                    activateTab(stepNumber);
                }
                $log.info("recipe initialized for modyfication " + angular.toJson(data, true));
            }).
            error(function (data, status) {
                $log.warn("responseData " + angular.toJson(responseData));
                if (status == 401) {
                    $scope.clearSession();
                    $location.path('/login');
                } else {
                    $scope.setAlert({type: 'danger', msg: responseData.errorMessage});
                }
            });
    }

    // Pozostałe informacje związane z kartami
    $scope.tabs = [
        {active: true},
        {active: false}
    ];
    var setAllTabsInactive = function () {
        for (var i = 0; i < $scope.tabs[i].length; i++) {
            $scope.tabs[i].active = false;
        }
    };
    var activateTab = function (index) {
        $scope.tabs[index].active = true;
    };
    var removeTab = function (index) {
        $scope.tabs.splice(index, 1);
    };
    var addNewTab = function () {
        $scope.tabs.push({active: true});
    };

    $scope.addRecipeStep = function () {
        $log.info("Adding step");
        var newStepNumber = $scope.recipe.recipeSteps.length + 1;
        $scope.recipe.recipeSteps.push({
            stepNumber: newStepNumber, title: '', description: '', ingredients: []
        });
        addNewTab();
        setAllTabsInactive();
        activateTab($scope.tabs.length - 1);
    };

    $scope.removeRecipeStep = function (index) {
        setAllTabsInactive();
        removeTab(index + 1);

        var recipeSteps = $scope.recipe.recipeSteps;
        recipeSteps.splice(index, 1);
        for (var i = index; i < recipeSteps.length; i++) {
            recipeSteps[i].stepNumber--;
        }

        if (index < recipeSteps.length) {
            activateTab(index + 1);
        } else {
            activateTab(recipeSteps.length);
        }
    };

    $scope.saveRecipe = function () {

        var isErrorInRecipe = false;
        var lastErrorTabIndex = 0;
        if (!$scope.form.recipeForm.$valid) {
            $scope.form.recipeForm.$submitted = true;
            isErrorInRecipe = true;
        }
        for (var i = 0; i < $scope.recipe.recipeSteps.length; i++) {
            var recipeStepForm = $scope.$eval('form.recipeStepForm_' + i);
            if (!recipeStepForm.$valid) {
                recipeStepForm.$submitted = true;
                lastErrorTabIndex = i + 1;
                isErrorInRecipe = true;
            }
        }
        if (isErrorInRecipe) {
            setAllTabsInactive();
            activateTab(lastErrorTabIndex);
            return;
        }
        $scope.recipe.cookingTime = $scope.tabs[0].cookingTime * 1000 * 60; // minutes to ms
        for (var i = 0; i < $scope.recipe.recipeSteps.length; i++) {
            $scope.recipe.recipeSteps[i].timerDuration = $scope.tabs[i + 1].timerDuration * 1000;  //seconds to ms
            $scope.recipe.recipeSteps[i].realizationTime = $scope.tabs[i + 1].realizationTime * 1000 * 60; //minutes to ms

        }
        $log.info("recipe to send " + angular.toJson($scope.recipe, true));

        if (recipeId !== undefined) {
            $http.put('/rest/recipes/' + recipeId, $scope.recipe)
                .success(function (responseData) {
                    $log.info("responseData " + responseData);
                    $location.path('/recipes/' + recipeId);
                }).error(function (responseData, status) {
                    $log.warn("responseData " + angular.toJson(responseData));
                    if (status == 401) {
                        $scope.clearSession();
                        $location.path('/login');
                    } else {
                        $scope.setAlert({type: 'danger', msg: responseData.errorMessage});
                    }
                });
        } else {
            $http.post('/rest/recipes', $scope.recipe)
                .success(function (responseData) {
                    $log.info("responseData " + responseData);
                    $location.path('/recipes/' + responseData.recipeId);
                }).error(function (responseData, status) {
                    $log.warn("responseData " + angular.toJson(responseData));
                    if (status == 401) {
                        $scope.clearSession();
                        $location.path('/login');
                    } else {
                        $scope.setAlert({type: 'danger', msg: responseData.errorMessage});
                    }
                });
        }
    };

    $scope.addIngredient = function (tabId, ingredientId) {
        $log.info("tabId " + tabId + "  ingredientId" + ingredientId);
        $log.info(angular.toJson($scope.tabs, true));
        if (!$scope.tabs[tabId].ingredients)
            return;

        var ingredientToAdd = $scope.tabs[tabId].ingredients[ingredientId];
        if (!ingredientToAdd || !ingredientToAdd.ingredientUnitId || !ingredientToAdd.ingredientAmount)
            return;

        ingredientToAdd.ingredientId = ingredientId;

        $log.info(angular.toJson(ingredientToAdd));
        $scope.recipe.recipeSteps[tabId - 1].ingredients.push(ingredientToAdd);
        $scope.tabs[tabId].ingredients[ingredientId] = undefined;
    };

    $scope.removeIngredient = function (tabId, ingredientIndex) {
        $log.info("tabId, ingredientIndex" + tabId + ", " + ingredientIndex);
        $scope.recipe.recipeSteps[tabId - 1].ingredients.splice(ingredientIndex, 1);
    };

    $scope.isAdded = function () {
        return $routeParams.recipeId == undefined
    };

    $scope.isModified = function () {
        return $routeParams.recipeId != undefined
    };

    $scope.ingredientsFilter = '';

    $scope.getRecipeTabTitle = function () {
        if ($scope.recipe.title !== undefined && $scope.recipe.title.length > 0) {
            return $scope.recipe.title;
        } else {
            return 'Main card';
        }
    };

    $scope.getRecipeStepTabTitle = function (index) {
        var recipeStep = $scope.recipe.recipeSteps[index];
        if (recipeStep !== undefined && recipeStep.title !== undefined && recipeStep.title.length > 0) {
            return recipeStep.title;
        } else {
            return 'Recipe step';
        }
    };

    if ($scope.isAdded()) {
        $timeout(function () {
            var element = $window.document.getElementById('helpButtonAdd');
            if (element)
                element.focus();
        });
    } else {
        $timeout(function () {
            var element = $window.document.getElementById('helpButtonEdit');
            if (element)
                element.focus();
        });
    }
}]);
