'use strict';

angular.module('gotowankoApp.userDetailsView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/users/:userId', {
            templateUrl: '/users/details/details.html',
            controller: 'UserDetailsController'
        });
    }])

    .controller('UserDetailsController', ['$scope', '$http', function ($scope, $http) {

    }]);