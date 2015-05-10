'use strict';

angular.module('gotowankoApp.loginView', ['ngRoute', 'ab-base64', 'ui.bootstrap'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: '/users/login/login.html',
            controller: 'LoginController'
        });
    }])

    .controller('LoginController', ['$scope', '$http', '$log', 'base64', function ($scope, $http, $log, base64) {
        $scope.gotowankoEmail = undefined;
        $scope.alerts = [
/*            {type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.'},
            {type: 'success', msg: 'Well done! You successfully read this important alert message.'}*/
        ];

        $scope.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.gotowankoLogin = function (email, password) {
            $scope.registrationForm.$setSubmitted();
            $scope.alerts = [];
            $log.info($scope);
            $log.info("email errors:" + $scope.registrationForm.email.$error);
            $log.info("pass errors:" + $scope.registrationForm.password.$error);
            /*
             for (var error in $scope.loginForm.email.$error) {
             var alert = { type: 'danger' }

             }
             }*/

            if (!$scope.registrationForm.email.$valid || !$scope.registrationForm.password.$valid)
                return;

            var encodedLoginData = base64.encode(email + ":" + password);

            $log.info(email + " " + password + " " + encodedLoginData)
            $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedLoginData;
            $http.post('/rest/sessions').
                success(function (data, status, headers, config) {
                    $scope.alerts = [];
                    $scope.alerts.push({type: 'success', msg: 'Login successful'});
                }).
                error(function (data, status, headers, config) {
                    $log.info(data + " " + status)
                    $scope.alerts = [];
                    $scope.alerts.push({type: 'danger', msg: 'Invalid email or password'});
                });
        };


    }]);