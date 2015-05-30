'use strict';

var m = angular.module('gotowankoApp.createRecipeView', ['ngRoute']);

m.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/recipes_create', {
        templateUrl: '/recipes/create/create.html',
        controller: 'CreateRecipeController'
    });
}])

m.controller('CreateRecipeController', ['$scope', '$http', '$log', function ($scope, $http, $log) {

    // Tworzony przez użytkownika Request Body
    $scope.recipe = {
        title: "Main",
        photoUrl: '',
        active: true,
        recipeSteps: [
            {
                stepNumber: 1, photoUrl: '', videoUrl: '', title: 'New step', description: '', ingredients: []
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
            $log.info("/rest/ingredients/list " + responseData)
            $scope.ingredients = responseData;
        });

    $http.get('/rest/ingredients/units')
        .success(function (responseData) {
            $log.info("/rest/ingredients/units " + responseData)

            $scope.ingredientUnits = responseData;
        });


    $scope.addRecipeStep = function () {

        var newStepNumber = $scope.recipe.recipeSteps.length + 1;
        $scope.recipe.recipeSteps.push({
            stepNumber: newStepNumber, photoUrl: '', videoUrl: '', title: 'New step', description: '', ingredients: []
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
        $http.post('/rest/recipes', $scope.recipe)
            .success(function (responseData) {
                $log.info("responseData " + responseData);

            }).error(function (responseData) {
                $log.warn("responseData " + responseData);
                $scope.setAlert({type: 'danger', msg: responseData.errorMessage});
            });
    };

    $scope.ingredientsFilter = '';
}]);