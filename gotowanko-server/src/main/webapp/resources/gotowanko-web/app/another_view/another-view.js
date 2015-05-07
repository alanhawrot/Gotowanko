'use strict';

angular.module('gotowankoApp.anotherView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/anotherPage', {
            templateUrl: 'another_view/another-view.html',
            controller: 'AnotherCtrl'
        });
    }])

    .controller('AnotherCtrl', ['$scope', function ($scope) {
        $scope.obiekt = "Test, jest OK";
    }]);