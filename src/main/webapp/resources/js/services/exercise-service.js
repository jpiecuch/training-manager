MetronicApp.service('exerciseService', function($http, urlService) {
    var FORM_INPUT_SET = "exerciseSet";

    this.isValid = function(exercise) {
        exercise.valid = exercise.sets.length > 0 && this.isValidInputs(exercise);
        return exercise.valid;
    }

    this.isValidInputs = function(exercise) {
        var validCounter = 0;
        for (var i = 0; i < exercise.sets.length; i++) {
            if (exercise.form[FORM_INPUT_SET + exercise.index + i] !== undefined
                && exercise.form[FORM_INPUT_SET + exercise.index + i].$touched
                && exercise.form[FORM_INPUT_SET + exercise.index + i].$valid) {
                validCounter++;
            }
        }
        return exercise.sets.length === validCounter;
    }

    this.post = function(exercise) {
        return $http.post(urlService.apiURL('/exercise'), {
            position: exercise.position,
            group: exercise.group,
            sets: exercise.sets,
            descriptionId: exercise.description.id,
            workoutId: exercise.workoutId,
            id: exercise.id
        }).then(function(data) {
            exercise.id = data.data;
            return data;
        });
    }


    this.get = function(description, form, index, exercise) {
        var me = this;
        var array = [];
        if (exercise) {
            for (var property in exercise.sets) {
                array.push(exercise.sets[property]);
            }
        } else {
            array = [null, null, null, null];
        }
        var result = {
            id: exercise ? exercise.id : undefined,
            index: index,
            form: form,
            description: description,
            visible: true,
            sets: array,
            group: exercise ? exercise.group : null,
            position: exercise ? exercise.position : null,
            addSet: function() {
                this.sets.push(0);
            },
            removeSet: function(idx) {
                this.sets.splice(idx, 1);
            },
            isValid: function() {
                return me.isValid(this);
            }
        }

        return result;
    }
});