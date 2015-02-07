MetronicApp.service('planStarterService', function(formValidateService, $http, authenticateService, urlService, alertService, $location) {
    this.get = function(planId) {
        var currentYear = moment().year();

        return {
            planId: planId,
            years: [currentYear, currentYear + 1, currentYear + 2],
            weeks: null,
            week: null,
            year: null,
            getWeeks: function() {
                this.weeks = [];
                for(var i = 1; i <= Math.max(moment(new Date(this.year, 11, 31)).isoWeek(), moment(new Date(this.year, 11, 31-7)).isoWeek()); i++) {
                    this.weeks.push(i);
                }
                this.week = null;
            },
            start: function(form) {
                var me = this;
                formValidateService.validate(form);
                if (form.$valid) {
                    authenticateService.signed().then(function(data) {
                        $http.post(urlService.apiURL('/users/' + data.id + '/plans'),  {year: me.year, week: me.week, planId: me.planId}).then(function(data) {
                            alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                            $location.path('/calendar');
                        });
                    });
                } else {
                    alertService.show({type: 'warning', title: 'ERROR', description: 'Something is wrong'});
                }
            }
        }
    }
});