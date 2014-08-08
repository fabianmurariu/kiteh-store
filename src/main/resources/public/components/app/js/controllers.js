'use strict';

var tevinziControllers = angular.module("tevinziControllers", []);

tevinziControllers.controller('PostController', ['$scope', 'PostService', function ($scope, postService) {
    var position = {
        limit: 5,
        offset: 0,
        next: function (actualLimit) {
            if (actualLimit > 0) {
                this.offset = this.offset + Math.min(this.limit, actualLimit) + 1;
            }
        }
    };

    $scope.posts = [];
    $scope.loadMore = function () {
        postService.posts(position.limit, position.offset).then(function (newPosts) {
            $scope.posts = $scope.posts.concat(newPosts);
            position.next(newPosts.length);
        }, function (err) {
            $scope.errorMsg = "Can't reach server for now, please try later"
        });
    };

    $scope.loadMore()

}]);

tevinziControllers.controller('LoginController', ['$scope', '$window', '$timeout', '$cookies', "$rootScope", function ($scope, $window, $timeout, $cookies, $rootScope) {
    $scope.isLoggedIn = function () {
        return $cookies.authKey !== undefined && $cookies.authKey !== 'None';
    };

    $scope.isLoggedOut = function () {
        return $cookies.authKey === 'None' || $cookies.authKey === undefined
    };

    $rootScope.$on("doneLogin", function(event, errMsg){
        var modal = $('#signup-modal');
        modal.foundation('reveal', 'close');
        $scope.$apply(function(){
            $scope.errorMsg = errMsg;
        });
        $timeout(function () {
            $scope.errorMsg = undefined;
        }, 5000);
    });

    $scope.dismissLogin = function (loginWindow, errMsg) {
        loginWindow.close();
        $rootScope.$broadcast("doneLogin", errMsg);
    };

    $scope.select = function (provider) {
        if (provider === 'google') {
            $scope.loginDialog = '/signup/google';
        } else if (provider === 'facebook') {
            $scope.loginDialog = '/signup/facebook';
        } else {
            $scope.loginDialog = ''
        }
        if ($scope.loginDialog !== '') {
            var loginWindow = $window.open($scope.loginDialog, "_blank", "width=780,height=410,toolbar=0,scrollbars=0,status=0,resizable=0,location=0,menuBar=0");
            loginWindow.onbeforeunload = function () {
                if ($cookies.authKey === 'None' || $cookies.authKey === undefined) {
                    $scope.authReason = "Fail to authenticate no response from server";
                    $scope.dismissLogin(loginWindow, $scope.authReason);
                } else if ($cookies.authKey !== undefined && $cookies.authKey !== 'None') {
                    /* success */
                    $scope.authReason = "Success";
                    $scope.dismissLogin(loginWindow, undefined);
                } else {
                    $scope.authReason = "Unknown";
                    $scope.dismissLogin(loginWindow, "Unable to authenticate");
                }
            };
        }
    };

    $scope.profile = function () {

    }
}]);
