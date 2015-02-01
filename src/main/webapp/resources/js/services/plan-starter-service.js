MetronicApp.service('planStarterService', function() {
    this.get = function(planId) {
        var currentYear = moment().year();

        return {
            planId: planId,
            years: [currentYear, currentYear + 1, currentYear + 2],
            weeks: undefined,
            week: null,
            year: null,
            getWeeks: function() {
                this.weeks =[]
                for(var i = 1; i <= Math.max(moment(new Date(this.year, 11, 31)).isoWeek(), moment(new Date(this.year, 11, 31-7)).isoWeek()); i++) {
                    this.weeks.push(i);
                }
            }
        }
    }
});