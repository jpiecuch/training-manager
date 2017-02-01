app.service('accountRecordService', function($http, urlService, authenticateService, formValidateService, alertService) {

    this.get = function(name, type) {
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
            name: name,
            type: type,
            create: function(form) {
                formValidateService.validate(form);
                if (form.$valid) {
                    var payload = {
                        value: this.value,
                        date: this.date.value,
                        type: this.type
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

    this.count = function(params) {
        return $http.get(urlService.apiURL('/users/' + authenticateService.signed().id +'/records/count'), { params: params });
    };
});