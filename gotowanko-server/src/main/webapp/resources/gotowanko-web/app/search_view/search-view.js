'use strict';

angular.module('gotowankoApp.searchView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/search', {
            templateUrl: 'search_view/search-view.html',
            controller: 'SearchCtrl'
        });
    }])

    .controller('SearchCtrl', ['$scope', '$http', function ($scope, $http) {
        $scope.sort = 'dateAdded';

        $scope.searchRecipes = function (query, page) {
            var sort = $scope.sort;
            switch (sort) {
                case 'dateAdded': sort = 'BY_DATE_ADDED';
                    break;
                case 'title': sort = 'BY_TITLE_ALPHABETICALLY';
                    break;
                case 'numberOfLikes': sort = 'BY_NUMBER_OF_LIKES';
                    break;
                default: sort = 'BY_DATE_ADDED';
            }

            if (query === undefined) {
                query = '';
            }

            if (page === undefined) {
                page = 1;
            }
            var encodedQuery = encodeURIComponent(query.replace(/ /g, '*'));

            var searchUrl = '/rest/recipes?query=' + encodedQuery + '&sort=' + sort + '&page=' + page;

            $http.get(searchUrl).success(function (data) {
                $scope.recipes = data.content;
                $scope.links = data.links;
            });
        };
    }]);