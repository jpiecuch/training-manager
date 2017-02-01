'use strict';

app.controller('DashboardController', function($scope, userWorkoutService, accountRecordService, chartService, $location, $filter, $rootScope) {
    
    $scope.init = function() {
        $scope.dashboard = {};
         userWorkoutService.retrieve({from: moment(new Date()).format('YYYY-MM-DD'), state: ['PLANNED', 'IN_PROGRESS'], maxResults: 5}).then(function(data) {
             $scope.dashboard.nextWorkouts = data.data;
         });
    };
    
    
});