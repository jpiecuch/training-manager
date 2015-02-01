'use strict';

MetronicApp.controller('WorkoutController', function($scope, $stateParams, userWorkoutService) {


    $scope.init = function() {
        userWorkoutService.get($stateParams.id).then(function(data) {
            $scope.workout = data.data;
            $scope.form = {};
        });
    };

    /*$scope.$watch('workout', function(workout) {
        if (workout) {
            for (var i = 0; i < workout.executions.length; i++) {
                var execution = workout.executions[i];
                console.log(execution.id);
                $scope.$watch('execution-form' + execution.id, function(executionForm) {
                    console.log(executionForm);
                });
            }
        }
    });*/

    $scope.$watch('form', function(executionForm) {
        console.log(executionForm);
    });
});