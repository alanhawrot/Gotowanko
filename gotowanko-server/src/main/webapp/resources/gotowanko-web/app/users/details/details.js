'use strict';

angular.module('gotowankoApp.userDetailsView', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/users/:userId', {
            templateUrl: '/users/details/details.html',
            controller: 'UserDetailsController'
        });
    }])

    .controller('UserDetailsController', ['$scope', '$routeParams', '$http', function ($scope, $routeParams, $http) {
        $scope.alerts = [
            /*            {type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.'},
             {type: 'success', msg: 'Well done! You successfully read this important alert message.'}*/
        ];

        var getUserUrl = '/rest/users/' + $routeParams.userId;

        $http.get(getUserUrl).success(function (data) {
            $scope.user = data;
        });
    }]);