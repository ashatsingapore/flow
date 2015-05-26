'use strict';

angular.module('leanflowApp')
    .factory('ProcessStep', function ($resource) {
        return $resource('api/processSteps/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if (data.creationTime != null) data.creationTime = new Date(data.creationTime);
                    if (data.acceptedTime != null) data.acceptedTime = new Date(data.acceptedTime);
                    if (data.completedTime != null) data.completedTime = new Date(data.completedTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
