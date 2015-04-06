'use strict';

app.controller('DashboardController', function($scope, userWorkoutService, $location) {
    $scope.dashboard = {
        goToWorkout: function(id) {
            $location.url('/workout/' +id);
        }
    };

    $scope.init = function() {
         userWorkoutService.retrieve({from: moment(new Date()).format('YYYY-MM-DD'), maxResults: 5}).then(function(data) {
             $scope.dashboard.nextWorkouts = data.data;
         });
    }
});