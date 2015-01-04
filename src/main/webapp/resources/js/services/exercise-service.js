MetronicApp.service('exerciseService', function() {
    var FORM_INPUT_SET = "exerciseSet";

    this.isValid = function(exercise) {
        exercise.valid = exercise.sets.length > 0 && this.isValidInputs(exercise);
        return exercise.valid;
    }

    this.isValidInputs = function(exercise) {
        for (var i = 0; i < exercise.sets.length; i++) {
            if (exercise.form[FORM_INPUT_SET + exercise.index + i] === undefined || !exercise.form[FORM_INPUT_SET + exercise.index + i].$touched) {
                return false;
            }
        }
        return exercise.sets.length > 0;
    }


    this.get = function(description, form, index) {
        var me = this;
        return {
            index: index,
            form: form,
            description: description,
            sets: [null, null, null, null],
            addSet: function() {
                this.sets.push(0);
            },
            removeSet: function(idx) {
                this.sets.splice(idx, 1);
            },
            isValid: function() {
                return me.isValid(this);
            }
        };
    }
});