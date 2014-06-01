'use strict';

describe('tevinzi.ro controllers', function () {
    beforeEach(module('tevinziApp'));

    describe('PostListCtl', function () {

        var postController, scope, postService, deferred;

        beforeEach(inject(function ($controller, $rootScope, $q) {
            postService = {
                posts: function (offset, limit) {
                    deferred = $q.defer();
                    return deferred.promise;
                }
            };

            scope = $rootScope.$new();
            spyOn(postService, 'posts').andCallThrough();
            postController = $controller('PostController', {$scope: scope, 'PostService': postService});
        }));

        it('should return 10 mock posts', inject(function () {
            deferred.resolve(mock.posts);
            scope.$digest();
            expect(scope.posts).toEqual(mock.posts);
            expect(postService.posts).toHaveBeenCalled();
        }));

        it('limit and offset should increase on loadMore', inject(function(){
            deferred.resolve(mock.posts.slice(0,6));
            scope.$digest();
            expect(scope.posts).toEqual(mock.posts.slice(0,6));
            expect(postService.posts).toHaveBeenCalled();
            scope.loadMore();
            deferred.resolve(mock.posts.slice(6,11));
            scope.$digest();
            expect(scope.posts.length).toEqual(10);
            expect(scope.posts).toEqual(mock.posts);
        }));

    });

    describe('LoginController', function(){
        var loginController, scope, fakeWindow, fakeTimeout;

        beforeEach(inject(function($controller, $rootScope){
            scope = $rootScope;
            fakeWindow = {
              open : function(obj){}
            };

            fakeTimeout = function(fn, tm) {fn()}

            spyOn(fakeWindow, 'open').andCallThrough();
            loginController = $controller('LoginController', {$scope: $rootScope, $window: fakeWindow, $timeout: fakeTimeout})
        }));

        it('should select google', inject(function(){
            scope.select('google');
            expect(scope.loginDialog).toEqual('/signup/google');
            expect(fakeWindow.open).toHaveBeenCalled();
        }));

        it('should select facebook', inject(function(){
            scope.select('facebook');
            expect(scope.loginDialog).toEqual('/signup/facebook');
            expect(fakeWindow.open).toHaveBeenCalled();
        }));

        it('should empty for unknown', inject(function(){
            scope.select('microsoft');
            expect(scope.loginDialog).toEqual('');
        }));
    });
});