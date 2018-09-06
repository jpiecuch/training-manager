app.service('readService', function($http, userWorkoutService, executionService) {
    this.USER_WORKOUT = userWorkoutService;

    this.EXECUTION = executionService;
});