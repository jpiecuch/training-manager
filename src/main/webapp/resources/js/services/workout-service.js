MetronicApp.service('workoutService', function(exerciseService) {
    var FORM_INPUT_MUSCLE = "workoutMuscles";

    this.isValid = function(workout) {
        var result =  workout.exercises.length > 0;
        for (var i = 0; i < workout.exercises.length; i++) {
            var exercise = workout.exercises[i];
            if (!exerciseService.isValid(exercise)) {
                result = false;
            }
        }
        var validInputs = this.isValidInputs(workout);

        workout.valid = result && validInputs;
        return workout.valid;
    }

    this.isValidInputs = function(workout) {
         return workout.form[FORM_INPUT_MUSCLE + workout.index] !== undefined
             && workout.form[FORM_INPUT_MUSCLE + workout.index].$touched
             && workout.form[FORM_INPUT_MUSCLE + workout.index].$valid;
    }

    this.get = function(weekDay, form, index) {
        var me = this;
        return {
            index: index,
            form: form,
            muscles: null,
            exercises: [],
            weekDay: weekDay,
            isValid: function() {
                return me.isValid(this);
            },
            addExercise: function (description, descriptions) {
                this.exercises.push(exerciseService.get(description, this.form, '' + this.index + this.exercises.length));
                var reduceDescriptions = []
                for (var i = 0; i < descriptions.result[this.index].result.length; i++) {
                    if (description.id !== descriptions.result[this.index].result[i].id) {
                        reduceDescriptions.push(descriptions.result[this.index].result[i]);
                    } else {
                        descriptions.params[this.index].excludeId.push(description.id);
                    }
                }
                descriptions.result[this.index].result = reduceDescriptions;

            }
        }
    }
});