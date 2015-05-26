'use strict';

angular.module('leanflowApp')
    .controller('ProcessStepController', function ($scope, ProcessStep, Process, Authority, User) {
        $scope.processSteps = [];
        $scope.processs = Process.query();
        $scope.authoritys = Authority.query();
        $scope.users = User.query();
        $scope.loadAll = function() {
            ProcessStep.query(function(result) {
               $scope.processSteps = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            ProcessStep.get({id: id}, function(result) {
                $scope.processStep = result;
                $('#saveProcessStepModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.processStep.id != null) {
                ProcessStep.update($scope.processStep,
                    function () {
                        $scope.refresh();
                    });
            } else {
                ProcessStep.save($scope.processStep,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            ProcessStep.get({id: id}, function(result) {
                $scope.processStep = result;
                $('#deleteProcessStepConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ProcessStep.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProcessStepConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveProcessStepModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.processStep = {name: null, title: null, description: null, creationTime: null, acceptedTime: null, completedTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
