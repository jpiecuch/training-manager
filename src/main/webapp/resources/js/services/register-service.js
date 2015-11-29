app.service('registerService', function($http, urlService, formValidateService, inputValidateService, alertService, $filter) {

    this.post = function(user) {
        return $http.post(urlService.apiURL('/signon'), user);
    };

    this.success = function(form, response) {
        alertService.show({type: 'success', title: 'success', description: 'register.success'});
    };

    this.error = function(form, response) {
        _.each(response.data.fieldErrors, function(error) {
            form.errors[error.field].push(error);
        });
        alertService.show({type: 'warning', title: 'warning', description: 'register.validation.error'});
    };

    this.clearErrors = function(errors) {
      for (var property in errors) {
          if (Array.isArray(errors[property])) {
              errors[property] = [];
          }
      }
    };

    this.get = function() {
        var me = this;
        return {
            user: {
                username: null,
                firstName: null,
                lastName: null,
                email: null,
                password: null,
                accepted: false
            },
            errors: {
                username: [],
                firstName: [],
                lastName: [],
                email: [],
                password: [],
                accepted: [],
                resolveMessage: function(error) {
                    var message = $filter('translate')('user.' + error.field + '.' + error.code + '.error');
                    if (error.params) {
                        for (var property in error.params) {
                            message = message.replace('{' + property + '}', error.params[property]);
                        }
                    }
                    return message;
                },
                hasErrors: function(input) {
                    return this[input.$name];
                }
            },
            form: null,
            submit: function() {
                var form = this;
                me.clearErrors(this.errors);
                me.post(angular.extend({provider: 'LOCAL'},this.user)).then(function success(response) {
                    me.success(this.form, response);
                }, function error(response) {
                    me.error(form, response);
                });
            },
            init: function(form) {
                this.form = form;
            }
        }
    };
});