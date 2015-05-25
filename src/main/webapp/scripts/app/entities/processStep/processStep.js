'use strict';

angular.module('leanflowApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('processStep', {
                parent: 'entity',
                url: '/processStep',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'leanflowApp.processStep.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/processStep/processSteps.html',
                        controller: 'ProcessStepController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('processStep');
                        return $translate.refresh();
                    }]
                }
            })
            .state('processStepDetail', {
                parent: 'entity',
                url: '/processStep/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'leanflowApp.processStep.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/processStep/processStep-detail.html',
                        controller: 'ProcessStepDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('processStep');
                        return $translate.refresh();
                    }]
                }
            });
    });
