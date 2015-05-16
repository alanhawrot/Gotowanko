'use strict';

angular.module('gotowankoApp.loginView', ['ngRoute', 'ab-base64', 'ui.bootstrap', 'ngCookies'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: '/users/login/login.html',
            controller: 'LoginController'
        });
    }])

    .controller('LoginController', ['$scope', '$rootScope', '$http', '$log', 'base64', '$cookieStore', '$location',
        function ($scope, $rootScope, $http, $log, base64, $cookieStore, $location) {
            $scope.gotowankoEmail = undefined;
            $scope.$parent.alerts = [
                /*            {type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.'},
                 {type: 'success', msg: 'Well done! You successfully read this important alert message.'}*/
            ];

            $scope.$parent.closeAlert = function (index) {
                $scope.$parent.alerts.splice(index, 1);
            };

            $scope.gotowankoLogin = function (email, password) {
                $scope.registrationForm.$setSubmitted();
                $scope.$parent.alerts = [];
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
                                $scope.$parent.alerts = [];
                                $scope.$parent.alerts.push({type: 'success', msg: 'Login successful'});
                                $cookieStore.put('current.user', data);
                                $scope.$parent.loggedUser = data;
                                $log.info(status + ": " + data);
                                $location.path("/");
                            })
                            .error(function (data, status, headers, config) {
                                $log.warn(status + ": " + data);
                            });
                    })
                    .error(function (data, status, headers, config) {
                        $log.info(data + " " + status);
                        $scope.$parent.alerts = [];
                        $scope.$parent.alerts.push({type: 'danger', msg: 'Invalid email or password'});
                    });
            };
        }]);

