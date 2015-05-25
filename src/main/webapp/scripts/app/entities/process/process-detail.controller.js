'use strict';

angular.module('leanflowApp')
    .controller('ProcessDetailController', function ($scope, $stateParams, Process, ProcessStep, Authority) {
        $scope.process = {};
        $scope.load = function (id) {
            Process.get({id: id}, function(result) {
              $scope.process = result;
            });
        };
        $scope.load($stateParams.id);
    });
