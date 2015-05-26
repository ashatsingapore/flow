'use strict';

angular.module('leanflowApp')
    .controller('ProcessStepDetailController', function ($scope, $stateParams, ProcessStep, Process, Authority, User) {
        $scope.processStep = {};
        $scope.load = function (id) {
            ProcessStep.get({id: id}, function(result) {
              $scope.processStep = result;
            });
        };
        $scope.load($stateParams.id);
    });
