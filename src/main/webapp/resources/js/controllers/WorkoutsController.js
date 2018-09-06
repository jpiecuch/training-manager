'use strict';

app.controller('WorkoutsController', function($scope, userWorkoutService, queryValueService) {
    
    $scope.init = function(data) {
        userWorkoutService.retrieve(queryValueService.query(data.query)).then(function(data) {
            $scope.workouts = data.data;
        });
    };
    

    

    
});