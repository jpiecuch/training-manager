'use strict';

app.controller('LoginController', function($state, $scope, $http, $rootScope, urlService, authenticateService, resourceService, inputValidateService, policyService) {
    $scope.login = {
        tab: {
            idx: 0,
            setIdx: function(idx) {
                this.idx = idx;
            }
        },
        social: {
            providers:  [],
            url: ''
        },
        reset: {
            post: function() {
                authenticateService.resetPassword(this.email).then(function(data) {});
            },
            email: null
        },
        create: {
            post: function() {
                var me = this;
                authenticateService.create(this.login.create.user).then(function(data) {
                    me.login.create.status = 'CREATED';
                });
            },
            user: null,
            status: null
        },
        credentials: {
            username: null,
            password: null,
            provider: 'LOCAL',
            rememberMe: undefined
        },
        signIn: function() {
            console.log('test');
          authenticateService.signIn($scope.login.credentials).then(function(data) {
              if (data.status === 201) {
                    console.log($rootScope.settings.user);
                    $state.go('index');
                }
          });
        },
        input: {
            isNotValid: function(input) {
                return inputValidateService.isNotValid(input);
            },
            status: function(input) {
                return inputValidateService.status(input);
            }
        },
        policy: {
            show: function() {
                policyService.show();
            }
        }
    };

    $scope.init = function() {
        authenticateService.getSocials().then(function(data) {
            $scope.login.social.providers = data.data;
            $scope.login.social.url = urlService.url('/signin');
        });
    };
});