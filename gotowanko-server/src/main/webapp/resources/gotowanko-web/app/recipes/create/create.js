'use strict';

angular.module('gotowankoApp.createRecipeView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/recipes_create', {
            templateUrl: '/recipes/create/create.html',
            controller: 'CreateRecipeController'
        });
    }])

    .controller('CreateRecipeController', ['$scope', '$http', function ($scope, $http) {
        $scope.recipeInfo = {title: "Main", approximateCost: 0, photoUrl: '', cookingTime: 0, active: true};

        $scope.recipeSteps =
            [
                {
                    stepNumber: 1, photoUrl: '', videoUrl: '', timerDuration: 0, realizationTime: 0,
                    title: 'New step', description: '', active: false, ingredients: []
                }
            ];

        var ingredients = [];
        $http.get('/rest/ingredients')
            .success(function (dataResponseIngredients) {
                $http.get('/rest/ingredients/units')
                    .success(function (dataResponseIngredientUnits) {
                        angular.forEach(dataResponseIngredients, function (ingredient) {
                            ingredient.amount = 0;
                            ingredient.units = [];
                            ingredient.isChecked = false;
                            angular.copy(dataResponseIngredientUnits.ingredientUnits, ingredient.units);
                        });
                        ingredients = dataResponseIngredients;
                        angular.copy(ingredients, $scope.recipeSteps[0].ingredients);
                    });
            });

        var setAllInactive = function () {
            $scope.recipeInfo.active = false;
            angular.forEach($scope.recipeSteps, function (recipeStep) {
                recipeStep.active = false;
            });
        };

        var addNewRecipeStep = function () {
            var newStepNumber = $scope.recipeSteps.length + 1;
            var copyIngredients = [];
            angular.copy(ingredients, copyIngredients);
            $scope.recipeSteps.push({
                stepNumber: newStepNumber, photoUrl: '', videoUrl: '', timerDuration: 0,
                realizationTime: 0, title: 'New step', description: '', active: true, ingredients: copyIngredients
            });
        };

        $scope.addRecipeStep = function () {
            setAllInactive();
            addNewRecipeStep();
        };

        $scope.removeRecipeStep = function (index) {
            setAllInactive();
            $scope.recipeSteps.splice(index, 1);
            for (var i = index; i < $scope.recipeSteps.length; i++) {
                $scope.recipeSteps[i].stepNumber--;
            }

            if (index < $scope.recipeSteps.length) {
                $scope.recipeSteps[index].active = true;
            } else {
                $scope.recipeSteps[$scope.recipeSteps.length - 1].active = true;
            }
        };

        $scope.saveRecipe = function () {

        };

        $scope.ingredientsFilter = '';
    }]);