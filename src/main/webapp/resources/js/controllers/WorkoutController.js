'use strict';

MetronicApp.controller('WorkoutController', function($scope, $stateParams, userWorkoutService) {


    $scope.init = function() {
        userWorkoutService.get($stateParams.id).then(function(data) {
           $scope.workout = data.data;
        });
    };
});