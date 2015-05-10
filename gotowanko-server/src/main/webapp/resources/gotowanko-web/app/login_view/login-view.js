'use strict';

angular.module('gotowankoApp.loginView', ['ngRoute', 'ab-base64', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'login_view/login-view.html',
            controller: 'SearchCtrl'
        });
    }])

    .controller('LoginController', ['$scope', '$http', function ($scope, $http) {
        $scope.searchRecipes = function (query) {
            var login = function() {
                var encodedLoginData = "";
                $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedLoginData;
            }
        };


    }]);