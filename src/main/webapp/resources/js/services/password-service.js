app.service('passwordService', function($http, authenticateService, $state, alertService) {

    this.post = function(email) {
        return authenticateService.resetPassword(email);
    };

    this.success = function() {
        alertService.show({type: 'success', title: 'success', description: 'password.reset.success'});
    };

    this.error = function() {
        alertService.show({type: 'warning', title: 'warning', description: 'password.reset.error'});
    };


    this.get = function() {
        var me = this;
        return {
            email: null,
            form: null,
            submit: function() {
                me.post(this.email).then(function success() {
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