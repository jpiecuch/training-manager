MetronicApp.service('phaseService', function($q, $http, workoutService, urlService) {
    var FORM_INPUT_DESCRIPTION = "phaseDescription";
    var FORM_INPUT_GOAL = "phaseGoal";
    var FORM_INPUT_WEEKS = "phaseWeeks";

    this.isValid = function(phase) {
        var result =  phase.workouts.length > 0;
        for (var i = 0; i < phase.workouts.length; i++) {
            var workout = phase.workouts[i];
            if (!workoutService.isValid(workout)) {
                result = false;
            }
        }
        phase.valid = result && this.isValidInputs(phase);
        return phase.valid;
    }

    this.isValidInputs = function(phase) {
        return phase.form[FORM_INPUT_DESCRIPTION + phase.index] !== undefined
            && phase.form[FORM_INPUT_DESCRIPTION + phase.index].$touched
            && phase.form[FORM_INPUT_DESCRIPTION + phase.index].$valid
            && phase.form[FORM_INPUT_GOAL + phase.index] !== undefined
            && phase.form[FORM_INPUT_GOAL + phase.index].$touched
            && phase.form[FORM_INPUT_GOAL + phase.index].$valid
            && phase.form[FORM_INPUT_WEEKS + phase.index] !== undefined
            && phase.form[FORM_INPUT_WEEKS + phase.index].$touched
            && phase.form[FORM_INPUT_WEEKS + phase.index].$valid;
    }

    this.post = function(phase) {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return $http.post(urlService.apiURL('/phase'), { description: phase.description, goal: phase.goal, weeks: phase.weeks, planId: phase.planId, position: phase.position, id: phase.id }).then(function(data) {
                phase.id = data.data;
                return data;
            });
        }).then(function(data) {
            var array = [];
            for (var i =0; i < phase.workouts.length; i++) {
                var workout = phase.workouts[i];
                workout.phaseId = data.data;
                array.push(workoutService.post(workout));
            }
            return $q.all(array);
        });
    }

    this.get = function(form, index, phase) {
        var me = this;
        var result = {
            id: phase ? phase.id : undefined,
            form: form,
            index: index,
            childIndex: 0,
            description: phase ? phase.description : null,
            goal: phase ? phase.goal : null,
            weeks: phase ? phase.weeks : null,
            workouts: [],
            visible: true,
            isValid: function() {
               return me.isValid(this);
            },
            containsWeekDay: function(weekDay) {
                for(var i =0; i < this.workouts.length; i++) {
                    var workout = this.workouts[i];
                    if (workout.weekDay === weekDay) {
                        return true;
                    }
                }
                return false;
            },
            isValidWeekDay: function(weekDay) {
                for(var i =0; i < this.workouts.length; i++) {
                    var workout = this.workouts[i];
                    if (workout.weekDay === weekDay) {
                        return workout.valid;
                    }
                }
            },
            addWorkout: function(weekDay, workout) {
                if (!this.containsWeekDay(weekDay)) {
                    var index = '' + this.index + this.childIndex++;
                    var me = this;
                    workoutService.get(weekDay, this.form, index, workout).then(function(data) {
                        me.workouts.push(data);
                    });

                } else {
                    for(var i =0; i < this.workouts.length; i++) {
                        var removeWorkout = this.workouts[i];
                        if (removeWorkout.weekDay === weekDay) {
                            this.workouts.splice(i, 1);
                            break;
                        }
                    }
                }
            }
        }
        if (phase) {
            for(var i = 0; i < phase.workouts.length; i++) {
                result.addWorkout(i, phase.workouts[i]);
            }
        }
        return result;
    }
});