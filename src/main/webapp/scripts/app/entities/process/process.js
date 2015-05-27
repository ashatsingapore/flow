'use strict';

angular.module('leanflowApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('process', {
                parent: 'entity',
                url: '/process',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'leanflowApp.process.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/process/process.html',
                        controller: 'ProcessController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('process');
                        return $translate.refresh();
                    }]
                }
            })
            .state('processDetail', {
                parent: 'entity',
                url: '/process/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'leanflowApp.process.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/process/process-detail.html',
                        controller: 'ProcessDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('process');
                        return $translate.refresh();
                    }]
                }
            });
    });
