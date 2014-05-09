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

tevinziControllers.controller('LoginController', ['$scope', '$window', function ($scope, $window) {
    $scope.select = function (provider) {
        if (provider === 'google') {
            $scope.loginDialog = '/signup/google';
        } else if (provider === 'facebook') {
            $scope.loginDialog = '/signup/facebook';
        } else {
            $scope.loginDialog = ''
        }
        if ($scope.loginDialog !== '') {
            $window.open($scope.loginDialog, "_blank", "width=780,height=410,toolbar=0,scrollbars=0,status=0,resizable=0,location=0,menuBar=0");
        }
    };
}]);