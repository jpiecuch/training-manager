MetronicApp.service('userWorkoutService', function($q, $http, urlService, authenticateService) {
    this.get = function(id) {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return authenticateService.signed();;
        }).then(function(data) {
            return $http.get(urlService.apiURL('/users/' + data.id + '/workouts/' + id))
                .then(function(data) {
                    data.data.isInSuperSet = function(execution) {
                        for(var j = 0; j < this.executions.length; j++) {
                            var temp = this.executions[j];
                            if (execution.exercise.position !== temp.exercise.position && execution.exercise.group === temp.exercise.group) {
                                return true;
                            }
                        }
                    };
                    data.data.isFirstInSuperSet = function(execution) {
                        for(var j = 0; j < this.executions.length; j++) {
                            var temp = this.executions[j];
                            if (execution.exercise.position !== temp.exercise.position && execution.exercise.group === temp.exercise.group && execution.exercise.position < temp.exercise.position) {
                                return true;
                            }
                        }
                    },
                    angular.forEach(data.data.executions, function(index) {
                        index.sets = index.sets ? index.sets : [];
                        index.addSet = function() {
                            this.sets.push(0);
                            this.weights.push(0)
                        };
                        index.weights = index.weights ? index.weights : [];
                        index.removeSet = function(idx) {
                            this.sets.splice(idx, 1);
                            this.weights.splice(idx,1);
                        };
                    });
                    return data;
                });
        });
    }
});