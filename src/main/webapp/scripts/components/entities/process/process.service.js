'use strict';

angular.module('leanflowApp')
    .factory('Process', function ($resource) {
        return $resource('api/processs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    if (data.creationTime != null) data.creationTime = new Date(data.creationTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
