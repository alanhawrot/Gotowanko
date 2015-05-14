'use strict';

angular.module('gotowankoApp.loginView', ['ngRoute', 'ab-base64', 'ui.bootstrap', 'ngCookies'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: '/users/login/login.html',
            controller: 'LoginController'
        });
    }])

    .controller('LoginController', ['$scope', '$http', '$log', 'base64', '$cookieStore', '$location',
        function ($scope, $http, $log, base64, $cookieStore, $location) {
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
                $log.info("email errors:" + angular.toJson($scope.registrationForm.email.$error));
                $log.info("pass errors:" + angular.toJson($scope.registrationForm.password.$error));

                if (!$scope.registrationForm.email.$valid || !$scope.registrationForm.password.$valid)
                    return;

                var encodedLoginData = base64.encode(email + ":" + password);

                $log.info(email + " " + password + " " + encodedLoginData);
                $http.defaults.headers.common['Authorization'] = 'Basic ' + encodedLoginData;
                $http.post('/rest/sessions')
                    .success(function (data, status, headers, config) {
                        $http.get('/rest/users/currently_logged')
                            .success(function (data, status, headers, config) {
                                $scope.alerts = [];
                                $scope.alerts.push({type: 'success', msg: 'Login successful'});
                                $cookieStore.put('current.user', data);
                                $log.info(status + ": " + data);
                                location.reload();
                                $location.path("/");
                            })
                            .error(function (data, status, headers, config) {
                                $log.warn(status + ": " + data);
                            });
                    })
                    .error(function (data, status, headers, config) {
                        $log.info(data + " " + status);
                        $scope.alerts = [];
                        $scope.alerts.push({type: 'danger', msg: 'Invalid email or password'});
                    });
            };
        }]);

