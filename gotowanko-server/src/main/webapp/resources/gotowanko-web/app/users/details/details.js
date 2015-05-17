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
            $scope.editButtonText = 'Edit your profile';
            $scope.isUserEditing = false;

            var userIdParam = $routeParams.userId;

            $scope.isOwner = $scope.$parent.loggedUser !== undefined && $scope.$parent.loggedUser.id == userIdParam;

            var usersUrl = '/rest/users/' + userIdParam;

            $http.get(usersUrl)
                .success(function (data) {
                    $scope.user = data;
                }).error(function (data) {
                    $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                });

            $scope.editProfile = function () {
                if ($scope.isUserEditing) {
                    $scope.detailsForm.$setSubmitted();
                    $scope.detailsForm.confirmPassword.$setValidity('parse', true);

                    if (!$scope.detailsForm.confirmPassword.$valid || !$scope.detailsForm.name.$valid || !$scope.detailsForm.email.$valid || !$scope.detailsForm.password.$valid)
                        return;

                    var putData = {'name': $scope.user.name, 'email': $scope.user.email, 'password': $scope.password};

                    $http.put(usersUrl, putData)
                        .success(function () {
                            $scope.setAlert({type: 'success', msg: 'Changes are saved'});
                            $scope.isUserEditing = false;
                            $scope.editButtonText = 'Edit your profile';

                            var encodedLoginData = base64.encode($scope.user.email + ":" + $scope.password);
                            $http.defaults.headers.common.Authorization = 'Basic ' + encodedLoginData;
                            $cookieStore.put('current.user', $scope.user);
                            $scope.$parent.loggedUser = $scope.user;

                            $route.reload();
                        })
                        .error(function (data, status) {
                            $log.info(data + " " + status);
                            $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                            if (status == 401) {
                                $scope.clearSession();
                                $location.path('/login');
                            }
                        });
                } else {
                    $scope.isUserEditing = true;
                    $scope.editButtonText = 'Save changes';
                }
            };

            $scope.deleteAccount = function () {
                $http.delete(usersUrl)
                    .success(function () {
                        $scope.clearSession();
                        $location.path('/search');
                    })
                    .error(function (data, status) {
                        $scope.setAlert({type: 'danger', msg: data[0].errorMessage});
                        if (status == 401) {
                            $scope.clearSession();
                            $location.path('/login');
                        }
                    });
            };
        }]);