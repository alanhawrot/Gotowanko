'use strict';

angular.module('gotowankoApp.searchView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/search', {
            templateUrl: 'search_view/search-view.html',
            controller: 'SearchCtrl'
        });
    }])

    .controller('SearchCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.searchRecipes = function (query) {
            var encodedQuery = encodeURIComponent(query.replace(/ /g, "*"));
            var searchUrl = "http://localhost:8080/rest/recipes?query=" + encodedQuery;

            $http.get(searchUrl).success(function (data) {
                $scope.recipes = data.content;
            });
        };
    }]);