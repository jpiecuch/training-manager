MetronicApp.service('userWorkoutService', function($q, $http, urlService, authenticateService, formValidateService, alertService) {
    this.get = function(id) {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return authenticateService.signed();
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
                        index.save = function(form) {
                            this.form = form;
                            formValidateService.validate(this.form);
                            if (this.form.$valid) {
                                $http.put(urlService.apiURL('/executions/' + this.id), {
                                    id: this.id,
                                    sets: this.sets,
                                    weights: this.weights,
                                    exercise: this.exercise,
                                    confirm: this.confirm,
                                    comment: this.comment
                                }).then(function() {
                                    alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                                });
                            } else {
                                alertService.show({type: 'warning', title: 'ERROR', description: 'Something is wrong'});
                            }
                        };
                        index.saveAndConfirm = function(form) {
                            this.confirm = true;
                            this.save(form);
                        }
                    });
                    return data;
                });
        });
    }
});