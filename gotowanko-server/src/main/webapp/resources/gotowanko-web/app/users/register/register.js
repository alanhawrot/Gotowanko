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
    $scope.$parent.alerts = [
        /*            {type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.'},
         {type: 'success', msg: 'Well done! You successfully read this important alert message.'}*/
    ];

    $scope.$parent.closeAlert = function (index) {
        $scope.$parent.alerts.splice(index, 1);
    };

    $scope.register = function () {
        $scope.registrationForm.$setSubmitted();
        $scope.$parent.alerts = [];
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
                $scope.$parent.alerts = [];
                $scope.$parent.alerts.push({type: 'success', msg: 'Registration successful'});
                $log.info(data);
            }).
            error(function (data, status, headers, config) {
                $log.info(data + " " + status);
                $scope.$parent.alerts = [];
                $scope.$parent.alerts.push({type: 'danger', msg: data.errorMessage});
            });
    };
}]);
