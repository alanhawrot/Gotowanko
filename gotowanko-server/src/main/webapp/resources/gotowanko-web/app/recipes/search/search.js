'use strict';

angular.module('gotowankoApp.searchView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/search', {
            templateUrl: '/recipes/search/search.html',
            controller: 'SearchController'
        });
    }])

    .controller('SearchController', ['$scope', '$http', function ($scope, $http) {
        $scope.sort = 'Newest';
        $scope.size = 20;

        $scope.searchRecipes = function (query, page) {
            var sort = $scope.sort;
            switch (sort) {
                case 'Newest':
                    sort = 'BY_DATE_ADDED';
                    break;
                case 'Alphabetical':
                    sort = 'BY_TITLE_ALPHABETICALLY';
                    break;
                case 'Most popular':
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
            } else if (page > $scope.totalPagesCollection.length) {
                page = $scope.totalPagesCollection.length;
            }

            var size = $scope.size;

            var searchUrl = '/rest/recipes?query=' + encodedQuery + '&sort=' + sort + '&page=' + page + '&size=' + size;

            $http.get(searchUrl).success(function (data) {
                $scope.recipes = data.content;
                $scope.totalPagesCollection = new Array(data.pageMetadata.totalPages);
                $scope.currentPage = data.pageMetadata.number;
            });
        };

        $scope.sendEmail = function (email) {
            window.location.href = "mailto:" + email;
        };
    }]);