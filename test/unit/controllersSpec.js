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

        it('limit and offset should increase on loadMore', inject(function () {
            deferred.resolve(mock.posts.slice(0, 6));
            scope.$digest();
            expect(scope.posts).toEqual(mock.posts.slice(0, 6));
            expect(postService.posts).toHaveBeenCalled();
            scope.loadMore();
            deferred.resolve(mock.posts.slice(6, 11));
            scope.$digest();
            expect(scope.posts.length).toEqual(10);
            expect(scope.posts).toEqual(mock.posts);
        }));

    });

    describe ('AuthService', function(){
       var authService, scope, fakeCookies;
        beforeEach(inject(function($controller, $rootScope, $cookies, _AuthService_){
            scope = $rootScope;
            fakeCookies = $cookies;
            authService = _AuthService_;
        }));

        it("should not be logged to start with", function(){
           expect(authService.isLogged()).toEqual(false);
        });

        it("should show logged in when success", function(){
            authService.success("1234");
            expect(authService.isLogged()).toEqual(true)
        });

        it("should after logout isLogged should be false", function(){
            authService.success("1234");
            authService.logout();
            expect(authService.isLogged()).toEqual(false)
        });

    });

    describe('LoginController', function () {
        var loginController, scope, fakeWindow, fakeTimeout, fakeCookies, popup;

        beforeEach(inject(function ($controller, $rootScope, _$window_) {
            scope = $rootScope;
            fakeWindow = {
                open: function(one, two, three){
                    popup = _$window_.open(one, two, three);
                    return  popup;
                },
                close:function(){
                    return _$window_.close();
                }
            };
            fakeCookies = {};
            fakeTimeout = function (fn, tm) {
                fn()
            };

            spyOn(fakeWindow, 'open').andCallThrough();
            spyOn(fakeWindow, 'close').andCallThrough();
            loginController = $controller('LoginController', {$scope: $rootScope, $window: fakeWindow, $timeout: fakeTimeout, $cookies: fakeCookies})
            spyOn(scope, 'dismissLogin').andCallFake(function(){});
        }));

        it("should be loggedOut", function(){
            expect(scope.isLoggedIn()).toEqual(false);
            expect(scope.isLoggedOut()).toEqual(true);
        });

        it('should select google', inject(function () {
            scope.select('google');
            expect(scope.loginDialog).toEqual('/signup/google');
            expect(fakeWindow.open).toHaveBeenCalled();
            if (popup != undefined){
                popup.close();
            }
        }));

        it('should select google Fail', inject(function () {
            fakeCookies.authKey = "None";
            scope.select('google');
            expect(scope.loginDialog).toEqual('/signup/google');
            expect(fakeWindow.open).toHaveBeenCalled();
            if (popup != undefined){
                popup.close();
            }
            expect(scope.dismissLogin).toHaveBeenCalled();
            expect(scope.authReason).toEqual("Fail to authenticate no response from server");
        }));

        it('should select google Success', inject(function () {
            fakeCookies.authKey = "123456789";
            scope.select('google');
            expect(scope.loginDialog).toEqual('/signup/google');
            expect(fakeWindow.open).toHaveBeenCalled();
            if (popup != undefined){
                popup.close();
            }
            expect(scope.dismissLogin).toHaveBeenCalled();
            expect(scope.authReason).toEqual("Success");
        }));

        it('should select facebook', inject(function () {
            scope.select('facebook');
            expect(scope.loginDialog).toEqual('/signup/facebook');
            expect(fakeWindow.open).toHaveBeenCalled();
            if (popup != undefined){
                popup.close();
            }
        }));

        it('should empty for unknown', inject(function () {
            scope.select('microsoft');
            expect(scope.loginDialog).toEqual('');
        }));
    });
});