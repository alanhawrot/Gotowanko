'use strict';

angular.module('gotowankoApp.userDetailsView', ['ngRoute', 'ngCookies', 'ab-base64'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/users/:userId', {
            templateUrl: '/users/details/details.html',
            controller: 'UserDetailsController'
        });
    }])

    .directive('detailsValidPasswordC', function () {
        return {
            require: 'ngModel',
            link: function (scope, elm, attrs, ctrl) {
                ctrl.$parsers.unshift(function (viewValue, $scope) {
                    var noMatch = viewValue != scope.detailsForm.password.$viewValue;
                    ctrl.$setValidity('noMatch', !noMatch)
                })
            }
        }
    })

    .controller('UserDetailsController', ['$scope', '$route', '$routeParams', '$http', '$cookieStore', '$location', 'base64',
        function ($scope, $route, $routeParams, $http, $cookieStore, $location, base64) {
            $scope.$parent.alerts = [
                /*            {type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.'},
                 {type: 'success', msg: 'Well done! You successfully read this important alert message.'}*/
            ];
            $scope.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            $scope.editButtonText = 'Edit your profile';
            $scope.isUserEditing = false;

            var usersUrl = '/rest/users/' + $routeParams.userId;

            $http.get(usersUrl).success(function (data) {
                $scope.user = data;
            });

            $scope.editProfile = function () {
                if ($scope.isUserEditing) {
                    $scope.detailsForm.$setSubmitted();
                    $scope.$parent.alerts = [];
                    $scope.detailsForm.confirmPassword.$setValidity('parse', true);

                    if (!$scope.detailsForm.confirmPassword.$valid || !$scope.detailsForm.name.$valid || !$scope.detailsForm.email.$valid || !$scope.detailsForm.password.$valid)
                        return;

                    var putData = {'name': $scope.user.name, 'email': $scope.user.email, 'password': $scope.password};

                    $http.put(usersUrl, putData)
                        .success(function () {
                            $scope.$parent.alerts = [];
                            $scope.$parent.alerts.push({type: 'success', msg: 'Changes are saved'});
                            $scope.isUserEditing = false;
                            $scope.editButtonText = 'Edit your profile';

                            var encodedLoginData = base64.encode($scope.user.email + ":" + $scope.password);
                            $http.defaults.headers.common.Authorization = 'Basic ' + encodedLoginData;
                            $cookieStore.put('current.user', $scope.user);
                            $scope.$parent.loggedUser = $scope.user;

                            $route.reload();
                        })
                        .error(function (data) {
                            $scope.$parent.alerts = [];
                            $scope.$parent.alerts.push({type: 'danger', msg: data[0].errorMessage});
                        });
                } else {
                    $scope.isUserEditing = true;
                    $scope.editButtonText = 'Save changes';
                }
            };

            $scope.deleteAccount = function () {
                $http.delete(usersUrl).success(function () {
                    $http.defaults.headers.common.Authorization = undefined;
                    $cookieStore.remove('current.user');
                    $cookieStore.remove('JSESSIONID');
                    $scope.$parent.loggedUser = undefined;
                    $location.path('/');
                });
            };
        }]);