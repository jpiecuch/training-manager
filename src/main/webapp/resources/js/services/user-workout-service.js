app.service('userWorkoutService', function($q, $http, urlService, authenticateService, formValidateService, alertService) {
    var states = {
        PLANNED: {
            key: 'PLANNED',
            color: { code: '#578ebe', name: 'blue-madison' },
            icon: 'fa fa-folder-o'
        },
        IN_PROGRESS: {
            key: 'IN_PROGRESS',
            color: { code: '#e87e04', name: 'yellow-gold' },
            icon: 'fa fa-spinner'
        },
        COMPLETED: {
            key: 'COMPLETED',
            color: { code: '#26c281', name: 'green-jungle' },
            icon: 'fa fa-check-square-o'
        },
        REJECTED: {
            key: 'REJECTED',
            color: { code: '#e04a49', name: 'red-intense' },
            icon: 'fa fa-ban'
        }
    };

    this.state = function(name) {
        return states[name];
    };

    this.allStates = function () {
        return states;
    };

    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/users/' + authenticateService.signed().id + '/workouts'), { params: params });
    };

    this.get = function (id) {
        return $http.get(urlService.apiURL('/users/' + authenticateService.signed().id + '/workouts/' + id))
            .then(function (data) {
                data.data.hasExecutionsDone = function() {
                    for (var j = 0; j < this.executions.length; j++) {
                        var temp = this.executions[j];
                        if (temp.state === states.PLANNED.key || temp.state === states.IN_PROGRESS.key) {
                            return false;
                        }
                    }
                    return true;
                };
                data.data.isInSuperSet = function (execution) {
                    for (var j = 0; j < this.executions.length; j++) {
                        var temp = this.executions[j];
                        if (execution.exercise.position !== temp.exercise.position && execution.exercise.group === temp.exercise.group) {
                            return true;
                        }
                    }
                };
                data.data.isFirstInSuperSet = function (execution) {
                    for (var j = 0; j < this.executions.length; j++) {
                        var temp = this.executions[j];
                        if (execution.exercise.position !== temp.exercise.position && execution.exercise.group === temp.exercise.group && execution.exercise.position < temp.exercise.position) {
                            return true;
                        }
                    }
                };
                data.data.start = function() {
                    data.data.state = states.IN_PROGRESS.key;
                    $http.put(urlService.apiURL('/users/' + authenticateService.signed().id + '/workouts/' + data.data.id), {
                        id: data.data.id,
                        comment: data.data.comment,
                        state: data.data.state

                    }).then(function () {
                        alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                    });
                };
                data.data.reject = function() {
                    data.data.state = states.REJECTED.key;
                    $http.put(urlService.apiURL('/users/' + authenticateService.signed().id + '/workouts/' + data.data.id), {
                        id: data.data.id,
                        comment: data.data.comment,
                        state: data.data.state

                    }).then(function () {
                        alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                    });
                };
                data.data.complete = function() {
                    data.data.state = states.COMPLETED.key;
                    $http.put(urlService.apiURL('/users/' + authenticateService.signed().id + '/workouts/' + data.data.id), {
                        id: data.data.id,
                        comment: data.data.comment,
                        state: data.data.state

                    }).then(function () {
                        alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                    });
                };
                angular.forEach(data.data.executions, function (index) {
                    index.visible = true;
                    index.sets = index.sets ? index.sets : angular.copy(index.exercise.sets);
                    angular.forEach(index.results, function(result) {
                        result.sets = result.sets.length > 0 ? result.sets : angular.copy(index.exercise.sets);
                        result.weights = result.weights.length > 0 ? result.weights : new Array(index.exercise.sets.length);
                    });
                    index.addSet = function (side) {
                        angular.forEach(this.results, function(index) {
                            if (index.side === null || side === index.side) {
                                index.sets.push(0);
                                index.weights.push(0);
                            }
                        });
                    };

                    index.removeSet = function (idx, side) {
                        angular.forEach(this.results, function(index) {
                            if (index.side === null || side === index.side) {
                                index.sets.splice(idx, 1);
                                index.weights.splice(idx, 1);
                            }
                        });
                    };
                    index.save = function (form) {
                        this.form = form;
                        formValidateService.validate(this.form);
                        if (this.state !== 'COMPLETED' || this.form.$valid) {
                            $http.put(urlService.apiURL('/executions/' + this.id), {
                                id: this.id,
                                results: this.results,
                                exercise: this.exercise,
                                state: this.state,
                                comment: this.comment
                            }).then(function () {
                                alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                            });
                        } else {
                            alertService.show({type: 'warning', title: 'ERROR', description: 'Something is wrong'});
                            this.state = 'IN_PROGRESS';
                        }
                    };
                    index.confirm = function (form) {
                        this.state = states.COMPLETED.key;
                        this.save(form);
                    };
                    index.reject = function (form) {
                        this.state = states.REJECTED.key;
                        this.save(form);
                    };
                    index.start = function (form) {
                        this.state = states.IN_PROGRESS.key;
                        this.save(form);
                    }
                });
                return data;
            });
    }
});