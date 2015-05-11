'use strict';

// Declare app level module which depends on views, and components
var m = angular.module('gotowankoApp', [
    'ngRoute',
    'ngCookies',
    'gotowankoApp.searchView',
    'gotowankoApp.loginView',
    'gotowankoApp.registrationView',
    'gotowankoApp.userDetailsView'
])
m.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.otherwise({redirectTo: '/search'});
}]);

m.controller('NavigationController', ['$scope', '$log', '$location', 'getUser', 'isLogged',
    function ($scope, $log, $location, getUser, isLogged) {
        $scope.user = getUser();
        $scope.isLogged = isLogged;

        $scope.isActive = function (viewLocation) {
            var active = (viewLocation === $location.path());
            $log.info($location.path() + ", active: " + active);
            return active;
        };
    }]);

m.controller('UserStatusController', ['$scope', '$log', 'isLogged', 'getUser', 'logout1',
    function ($scope, $log, isLogged, getUser, logout1) {
        $scope.isLogged = isLogged;
        $scope.user = getUser();
        $scope.logout1 = logout1;

    }]);

m.factory('isLogged', ['$log', 'getUser', function ($log, getUser) {
    return function () {
        return getUser() != undefined
    };
}]);

m.factory('getUser', ['$log', '$cookieStore', function ($log, $cookieStore) {
    return function () {
        return $cookieStore.get('current.user')
    };
}]);


m.factory('logout1', ['$http', '$log', '$cookieStore', '$location', function ( $http, $log, $cookieStore, $location) {
    return function () {
        $log.info("Logging out");
        $http.delete('/rest/sessions')
            .success(function (data, status, headers, config) {
                $cookieStore.remove('current.user');
                $log.info(data + " " + status);
                $scope.alerts = [];
                $scope.alerts.push({type: 'success', msg: 'Logout successful'});
                Session.clear();
                $location.path('/');
            })
            .error(function (data, status, headers, config) {
                $log.info(data + " " + status);
                $scope.alerts = [];
                $scope.alerts.push({type: 'danger', msg: data});
            });
    }

}]);