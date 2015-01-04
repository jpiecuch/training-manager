MetronicApp.service('phaseService', function(workoutService) {
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

    this.get = function(form, index) {
        var me = this;
        return {
            form: form,
            index: index,
            description: null,
            goal: null,
            weeks: null,
            weekDays: [],
            workouts: [],
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
            addWorkout: function(weekDay, descriptions) {
                if (!this.containsWeekDay(weekDay)) {
                    var index = '' + this.index + this.workouts.length;
                    descriptions.params[index] = {firstResult: 0, maxResults: 10, excludeId: []};
                    this.workouts.push(workoutService.get(weekDay, this.form, index));
                } else {
                    for(var i =0; i < this.workouts.length; i++) {
                        var workout = this.workouts[i];
                        if (workout.weekDay === weekDay) {
                            this.workouts.splice(i, 1);
                            break;
                        }
                    }
                }
            }
        }
    }
});