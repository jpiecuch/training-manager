app.service('dashboardService', function($http) {

    this.dashboard = function() {
        return {
            dashlet: {
                types: ['WORKOUTS', 'SERIAL-CHART', 'PIE-CHART', 'COLUMN-CHART']
            }
        };
    }
});