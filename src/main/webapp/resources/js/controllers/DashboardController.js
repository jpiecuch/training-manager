'use strict';

app.controller('DashboardController', function($scope, userWorkoutService, accountRecordService, chartService, $location, $filter, $rootScope) {

    $scope.dashboard = {
        goToWorkout: function(id) {
            $location.url('/workout/' +id);
        }
    };

    $scope.init = function(recordForm) {
         userWorkoutService.retrieve({from: moment(new Date()).format('YYYY-MM-DD'), state: ['PLANNED', 'IN_PROGRESS'], maxResults: 5}).then(function(data) {
             $scope.dashboard.nextWorkouts = data.data;
         });

        accountRecordService.retrieve({from: moment().subtract(12, 'months').format('YYYY-MM-DD'), type: ['WEIGHT'], maxResults: 300}).then(function(data) {

            var chart = chartService.get(data.data.result, $rootScope.settings.lang);

            $scope.$on('changeLang', function(event, args) {
                chart.language = args.lang;
                chart.validateData();
            });
        });

        $scope.$watch('recordForm', function(form) {
            if ($scope.dashboard.record === undefined && form !== undefined) {
                $scope.dashboard.record = accountRecordService.get(form);
            }
        });
    };
});