'use strict';

angular.module('leanflowApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


