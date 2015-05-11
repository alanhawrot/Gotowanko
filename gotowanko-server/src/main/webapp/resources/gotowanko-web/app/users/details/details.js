'use strict';

angular.module('gotowankoApp.userDetailsView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/users/:userId', {
            templateUrl: '/users/details/details.html',
            controller: 'UserDetailsController'
        });
    }])

    .controller('UserDetailsController', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {
        var getUserUrl = '/rest/users/' + $routeParams.userId;

        $http.get(getUserUrl).success(function (data) {
            $scope.user = data;
        });
    }]);