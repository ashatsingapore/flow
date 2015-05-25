'use strict';

angular.module('leanflowApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
