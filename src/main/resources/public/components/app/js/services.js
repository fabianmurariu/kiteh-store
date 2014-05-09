'use strict';

var tevinziServices = angular.module("tevinziServices", []);

tevinziServices.factory('PostService', ['Restangular', function (Restangular) {
    return {
        posts: function (limitN, offsetN) {
            return Restangular.all('posts').getList({limit: limitN, offset: offsetN});
        }
    }
}]);