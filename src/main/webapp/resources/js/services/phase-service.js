app.service('phaseService', function($q, $http, workoutService, urlService, inputService) {
    this.payload = function(phase) {
        var payload = { description: phase.description.value, goal: phase.goal.value, weeks: phase.weeks.value, planId: phase.planId, position: phase.position, id: phase.id, workouts: [] };
        _.each(phase.workouts, function(workout) {
            payload.workouts.push(workoutService.payload(workout));
        });
        return payload;
    };

    this.containsWeekDay = function(phase, weekDay) {
        for(var i =0; i < phase.workouts.length; i++) {
            var workout = phase.workouts[i];
            if (workout.weekDay === weekDay) {
                return true;
            }
        }
        return false;
    };

    this.isValidWeekDay =  function(phase, weekDay) {
        for(var i =0; i < phase.workouts.length; i++) {
            var workout = phase.workouts[i];
            if (workout.weekDay === weekDay) {
                return workout.valid;
            }
        }
    };

    this.addWorkout = function(phase, weekDay, workout) {
        phase.touched = true;
        var newWorkout = workoutService.get(weekDay, workout);
        phase.workouts.push(newWorkout);
        phase.activateWorkout(newWorkout);
    };

    this.removeWorkout = function(phase, weekDay) {
        phase.touched = true;
        for (var i =0; i < phase.workouts.length; i++) {
            var removeWorkout = phase.workouts[i];
            if (removeWorkout.weekDay === weekDay) {
                phase.workouts.splice(i, 1);
                break;
            }
            phase.activateWorkout(phase.workouts[0]);
        }
    };

    this.activateWorkout = function(phase, workout) {
        phase.workout = workout;
    };

    this.hasErrors = function(phase) {
        var hasErrors = undefined;
        if (phase.description.isValid() === false || phase.goal.isValid() === false || phase.weeks.isValid() === false) {
            hasErrors = true;
        } else if (phase.description.isValid() && phase.goal.isValid() && undefined && phase.weeks.isValid()) {
            hasErrors = false;
        }
        for (var i = 0; i < phase.workouts.length; i++) {
            if (phase.workouts[i].hasErrors()) {
                hasErrors = true;
                break;
            } else if (hasErrors === undefined && phase.workouts[i].hasErrors() === false) {
                hasErrors = false;
            }
        }
        return hasErrors !== undefined || phase.touched ? hasErrors || phase.workouts.length === 0 : hasErrors;
    };

    this.get = function(phase) {
        var me = this;
        var result = {
            id: phase ? phase.id : undefined,
            description: inputService.get(phase ? phase.description : null),
            goal: inputService.get(phase ? phase.goal : null),
            weeks: inputService.get(phase ? phase.weeks : null),
            workouts: [],
            position: phase ? phase.position : null,
            touched: false,
            isValid: function() {
               return me.isValid(this);
            },
            containsWeekDay: function(weekDay) {
                return me.containsWeekDay(this, weekDay);
            },
            isValidWeekDay: function(weekDay) {
                return me.isValidWeekDay(this, weekDay);
            },
            addWorkout: function(weekDay, workout) {
                me.addWorkout(this, weekDay, workout);
            },
            activateWorkout: function(workout) {
                me.activateWorkout(this, workout);
            },
            isWorkoutActive: function(workout) {
                return this.workout === workout;
            },
            removeWorkout: function(weekDay) {
                me.removeWorkout(this, weekDay);
            },
            updateWorkout: function(weekDay, workout) {
                if (!me.containsWeekDay(this, weekDay)) {
                    me.addWorkout(this, weekDay, workout)
                } else {
                    me.removeWorkout(this, weekDay);
                }
            },
            hasErrors: function() {
                return me.hasErrors(this);
            }
        };
        if (phase) {
            for(var i = 0; i < phase.workouts.length; i++) {
                result.addWorkout(i, phase.workouts[i]);
            }
            result.activateWorkout(result.workouts[0]);
        }
        return result;
    }
});