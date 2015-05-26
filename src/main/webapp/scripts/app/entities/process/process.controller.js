'use strict';

angular.module('leanflowApp')
    .controller('ProcessController', function ($scope, Process, User, ProcessStep) {
        $scope.processs = [];
        $scope.users = User.query();
        $scope.processsteps = ProcessStep.query();
        $scope.loadAll = function() {
            Process.query(function(result) {
               $scope.processs = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Process.get({id: id}, function(result) {
                $scope.process = result;
                $('#saveProcessModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.process.id != null) {
                Process.update($scope.process,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Process.save($scope.process,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Process.get({id: id}, function(result) {
                $scope.process = result;
                $('#deleteProcessConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Process.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProcessConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveProcessModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.process = {name: null, title: null, description: null, creationTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
