'use strict';

var m = angular.module('gotowankoApp.registrationView', ['ngRoute', 'ui.bootstrap', 'gotowankoApp.loginView'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/register', {
            templateUrl: '/users/register/register.html',
            controller: 'RegistrationController'
        });
    }]);

m.directive('validPasswordC', function () {
    return {
        require: 'ngModel',
        link: function (scope, elm, attrs, ctrl) {
            ctrl.$parsers.unshift(function (viewValue, $scope) {
                var noMatch = viewValue != scope.registrationForm.password.$viewValue
                ctrl.$setValidity('noMatch', !noMatch)
            })
        }
    }
});

m.controller('RegistrationController', ['$scope', '$http', '$log', 'base64', function ($scope, $http, $log, base64) {
    $scope.gotowankoEmail = undefined;

    $scope.register = function () {
        $log.info("name errors:" + angular.toJson($scope.registrationForm.name.$error));
        $log.info("email errors:" + angular.toJson($scope.registrationForm.email.$error));
        $log.info("pass errors:" + angular.toJson($scope.registrationForm.password.$error));
        $scope.registrationForm.confirmPassword.$setValidity('parse', true);
        $log.info("pass confirm errors:" + angular.toJson($scope.registrationForm.confirmPassword.$error));

        $log.info(
            $scope.registrationForm.confirmPassword.$valid + " " + $scope.registrationForm.name.$valid + " " + $scope.registrationForm.email.$valid + " " + $scope.registrationForm.password.$valid)
        if (!$scope.registrationForm.confirmPassword.$valid || !$scope.registrationForm.name.$valid || !$scope.registrationForm.email.$valid || !$scope.registrationForm.password.$valid)
            return;
        $log.info($scope.user);

        $http.post('/rest/users/', $scope.user).
            success(function (data, status, headers, config) {
                $scope.setAlert({type: 'success', msg: 'Registration successful'});
                $log.info(data);
            }).
            error(function (data, status, headers, config) {
                $log.info(data + " " + status);
                $scope.setAlert({type: 'danger', msg: data.errorMessage});
            });
    };
}]);
