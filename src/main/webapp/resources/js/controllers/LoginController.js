'use strict';

MetronicApp.controller('LoginController', function($state, $scope, $http, $rootScope, urlService, authenticateService, resourceService, inputValidateService, policyService) {
    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;

    $scope.login = {
        tab: {
            idx: 0,
            setIdx: function(idx) {
                this.idx = idx;
            }
        },
        bgs: '',
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
            provider: "LOCAL"
        },
        signIn: function(form) {
          authenticateService.signIn($scope.login.credentials).then(function(data) {
                if (data.data.entity) {
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
        resourceService.getAll('/resource/names/image/start-bg').then(function(response) {
            var bgs = [];
            for(var i = 0; i < response.data.length; i++) {
                bgs.push(urlService.url('resource/content/start-bg/') + response.data[i]);
            }
            $scope.login.bgs = bgs.join();
        });

        authenticateService.getSocials().then(function(data) {
            $scope.login.social.providers = data.data;
            $scope.login.social.url = urlService.url('/signin');
        });
    };
});