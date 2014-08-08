'use strict';

var tevinziServices = angular.module("tevinziServices", []);

tevinziServices.factory('PostService', ['Restangular', function (Restangular) {
    return {
        checkout : {},
        posts: function (limitN, offsetN) {
            return Restangular.all('posts').getList({limit: limitN, offset: offsetN});
        },
        addToCheckout : function(post) {

        }
    }
}]);

tevinziServices.factory('AuthService', ['$cookies', function ($cookies) {
    return {
        authKey : "None",
        success : function(key) {
            this.authKey = key;
        },
        logout : function(){
            this.authKey = "None";
            $cookies.authKey = "None";
        },
        isLogged: function() {
            return this.authKey !== undefined && this.authKey !== 'None'
        }

    }
}]);