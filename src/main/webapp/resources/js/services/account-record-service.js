app.service('accountRecordService', function($http, urlService, authenticateService, formValidateService, alertService) {

    this.get = function(form) {
        return {
            value: null,
            date: {
                value: null,
                opened: false,
                open: function($event) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    this.opened = true;
                },
                options: {
                    formatYear: 'yy',
                    startingDay: 1
                },
                format: 'dd/MM/yyyy'
            },
            form: form,
            create: function() {
                formValidateService.validate(this.form);
                if (this.form.$valid) {
                    var payload = {
                        value: this.value,
                        date: this.date.value,
                        type: 'WEIGHT'
                    };
                    $http.post(urlService.apiURL('/users/' + authenticateService.signed().id +'/records'), payload).then(function() {
                        alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                    });

                } else {
                    alertService.show({type: 'warning', title: 'ERROR', description: 'Something is wrong'});
                }
            }
        }
    };

    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/users/' + authenticateService.signed().id +'/records'), { params: params });
    };
});