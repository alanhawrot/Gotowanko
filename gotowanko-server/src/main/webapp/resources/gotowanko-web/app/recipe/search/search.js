'use strict';

angular.module('gotowankoApp.searchView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/search', {
            templateUrl: '/recipe/search/search.html',
            controller: 'SearchController'
        });
    }])

    .controller('SearchController', ['$scope', '$http', function ($scope, $http) {
        $scope.sort = 'Newest';

        $scope.searchRecipes = function (query, page) {
            var sort = $scope.sort;
            switch (sort) {
                case 'dateAdded':
                    sort = 'BY_DATE_ADDED';
                    break;
                case 'title':
                    sort = 'BY_TITLE_ALPHABETICALLY';
                    break;
                case 'numberOfLikes':
                    sort = 'BY_NUMBER_OF_LIKES';
                    break;
                default:
                    sort = 'BY_DATE_ADDED';
            }

            if (query === undefined) {
                query = '';
            }
            var encodedQuery = encodeURIComponent(query.replace(/ /g, '*'));

            if (page === undefined || page <= 0) {
                page = 1;
            } else if (page > $scope.totalPages.length) {
                page = $scope.totalPages.length;
            }

            var searchUrl = '/rest/recipes?query=' + encodedQuery + '&sort=' + sort + '&page=' + page;

            $http.get(searchUrl).success(function (data) {
                $scope.recipes = data.content;
                $scope.totalPages = new Array(data.pageMetadata.totalPages);
                $scope.currentPage = data.pageMetadata.number;
            });
        };
    }]);