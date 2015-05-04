app.service('exerciseService', function(inputService) {
    this.payload = function(exercise) {
        var sets = [];
        _.each(exercise.sets, function(item) {
            sets.push(item.value);
        });
        return {
            position: exercise.position,
            group: exercise.group,
            sets: sets,
            descriptionId: exercise.description.id,
            workoutId: exercise.workoutId,
            id: exercise.id
        };
    };

    this.addSet = function(exercise) {
        exercise.sets.push(inputService.get(null));
    };

    this.removeSet = function(exercise, idx) {
        exercise.sets.splice(idx, 1);
    };

    this.toFail = function(exercise, idx) {
        exercise.sets[idx].value = 'FAIL';
    };

    this.reps = function(exercise, idx) {
        exercise.sets[idx].value = null;
    };

    this.sets = function(exercise) {
        var array = [];
        if (exercise) {
            for (var property in exercise.sets) {
                array.push(inputService.get(exercise.sets[property]));
            }
        } else {
            array = [inputService.get(null), inputService.get(null), inputService.get(null), inputService.get(null)];
        }
        return array;
    };

    this.hasErrors = function(exercise) {
        var hasErrors = undefined;
        for (var i = 0; i < exercise.sets.length; i++) {
            if (exercise.sets[i].isValid() === false) {
                hasErrors = true;
                break;
            } else if (hasErrors === undefined && exercise.sets[i].isValid()) {
                hasErrors = false;
            }
        }
        return hasErrors;
    };

    this.get = function(exercise) {
        var me = this;
        var array = me.sets(exercise);
        var result = {
            id: exercise ? exercise.id : undefined,
            description: exercise.description,
            visible: true,
            sets: array,
            group: exercise ? exercise.group : null,
            position: exercise ? exercise.position : null,
            addSet: function() {
                me.addSet(this);
            },
            removeSet: function(idx) {
                me.removeSet(this, idx);
            },
            toFail: function(idx) {
                me.toFail(this, idx);
            },
            reps: function(idx) {
                me.reps(this, idx);
            },
            hasErrors: function() {
                return me.hasErrors(this);
            }
        };
        return result;
    }
});