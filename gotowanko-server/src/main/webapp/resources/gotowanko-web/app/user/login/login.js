'use strict';

angular.module('gotowankoApp.loginView', ['ngRoute', 'ab-base64'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'user/login/login.html',
            controller: 'LoginController'
        });
    }])

    .controller('LoginController', ['$scope', '$http', '$log', 'base64', function ($scope, $http, $log, base64) {
        $scope.gotowankoEmail = undefined;
        $scope.alerts = [];

        $scope.addAlert = function() {
            $scope.alerts.push({msg: 'Another alert!'});
        };

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.gotowankoLogin = function (gotowankoEmail, gotowankoPassword) {

            if(true) {
                $log.info($scope);
                /*
                 { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' },
                 { type: 'success', msg: 'Well done! You successfully read this important alert message.' }*/
                return;
            }

            var encodedLoginData = base64.encode(gotowankoEmail + ":" + gotowankoPassword);

            $log.info(gotowankoEmail + " " + gotowankoPassword + " " + encodedLoginData)
            $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedLoginData;

            $http.post('/sessions', null).
                success(function(data, status, headers, config) {

                }).
                error(function(data, status, headers, config) {

                });
        };


    }]);