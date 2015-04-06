'use strict';

app.controller('WorkoutController', function($scope, $stateParams, userWorkoutService, videoProviderService) {

    $scope.videoProvider = videoProviderService;

    $scope.init = function() {
        userWorkoutService.get($stateParams.id).then(function(data) {
            $scope.workout = data.data;
            $scope.form = {};
        });
    };

});