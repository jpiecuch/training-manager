app.service('signInService', function($http, authenticateService, $state, alertService) {

    this.post = function(credentials) {
        return authenticateService.signIn(credentials);
    };

    this.success = function() {
        $state.go('index');
    };

    this.error = function() {
        alertService.show({type: 'warning', title: 'warning', description: 'wrong.credentials'});
    };


    this.get = function() {
        var me = this;
        return {
            credentials: {
                username: null,
                password: null,
                provider: 'LOCAL',
                rememberMe: undefined
            },
            form: null,
            submit: function() {
                console.log('test');
                me.post(this.credentials).then(function success() {
                    me.success();
                }, function error() {
                    me.error();
                });
            },
            init: function(form) {
                this.form = form;
            }
        }
    };
});