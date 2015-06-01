'use strict';

var m = angular.module('gotowankoApp.createRecipeView', ['ngRoute']);

m.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/recipes_create', {
        templateUrl: '/recipes/addedit/addedit.html',
        controller: 'CreateRecipeController'
    });
}])

m.controller('CreateRecipeController', ['$scope', '$http', '$log', '$location', function ($scope, $http, $log, $location) {
    $scope.form = {};

    // Tworzony przez użytkownika Request Body
    $scope.recipe = {
        title: "Recipe title",
        active: true,
        recipeSteps: [
            {
                stepNumber: 1, title: 'First step', description: '', ingredients: []
            }
        ]
    };

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

    $http.get('/rest/ingredients/list')
        .success(function (responseData) {
            $log.info("/rest/ingredients/list " + angular.toJson(responseData, true));
            $scope.ingredients = responseData;
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


    $scope.addRecipeStep = function () {
        $log.info("Adding step")
        var newStepNumber = $scope.recipe.recipeSteps.length + 1;
        $scope.recipe.recipeSteps.push({
            stepNumber: newStepNumber, title: 'Next step', description: '', ingredients: []
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
        $log.info(angular.toJson($scope.recipe, true));

        if (!$scope.form.recipeForm.$valid) return;
        var lastErrorTabIndex = 0;
        var isErrorInRecipeStep = false;
        for (var i = 0; i < $scope.recipe.recipeSteps.length; i++) {
            var recipeStepForm = $scope.$eval('form.recipeStepForm_' + i);
            if (!recipeStepForm.$valid) {
                recipeStepForm.$submitted = true;
                lastErrorTabIndex = i + 1;
                isErrorInRecipeStep = true;
            }
        }
        if (isErrorInRecipeStep) {
            setAllTabsInactive();
            activateTab(lastErrorTabIndex);
            return;
        }

        var createRecipeRequest = angular.copy($scope.recipe);
        delete createRecipeRequest.active;
        $http.post('/rest/recipes', createRecipeRequest)
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
    }

    $scope.removeIngredient = function (tabId, ingredientIndex) {
        $log.info("tabId, ingredientIndex" + tabId + ", " + ingredientIndex);
        $scope.recipe.recipeSteps[tabId - 1].ingredients.splice(ingredientIndex, 1);
    }

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
    $scope.ingredientsFilter = '';
}]);
