MetronicApp.service('workoutService', function($q, $http, exerciseService, urlService, descriptionService) {
    var FORM_INPUT_MUSCLE = "workoutMuscles";

    var weekDaysMap = {
        0: 'SUNDAY', 1: 'MONDAY', 2: 'TUESDAY', 3: 'WEDNESDAY', 4: 'THURSDAY', 5: 'FRIDAY', 6: 'SATURDAY',
        SUNDAY: 0, MONDAY: 1, TUESDAY: 2, WEDNESDAY: 3, THURSDAY: 4, FRIDAY: 5, SATURDAY: 6
    }

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

    this.post = function(workout) {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return $http.post(urlService.apiURL('/workout'), {weekDay: weekDaysMap[workout.weekDay], muscles: workout.muscles, position: 1, phaseId: workout.phaseId, id: workout.id}).then(function(data) {
                workout.id = data.data;
                return data;
            });
        }).then(function(data) {
            var array = [];
            for (var i =0; i < workout.exercises.length; i++) {
                var exercise = workout.exercises[i];
                exercise.workoutId = data.data;
                array.push(exerciseService.post(exercise));
            }
            return $q.all(array);
        });
    }

    this.get = function(weekDay, form, index, workout) {
        var me = this;
        var result = {
            id: workout ? workout.id : undefined,
            index: index,
            childIndex: 0,
            form: form,
            muscles: workout ? workout.muscles : null,
            exercises: [],
            weekDay: workout ? weekDaysMap[workout.weekDay] : weekDay,
            visible: true,
            descriptions: {
                params: { firstResult: 0, maxResults: 10, excludeId: [] },
                result: {result: [], count: 0},
                retrieve: function() {
                    this.params.firstResult = 0;
                    var me = this;
                    descriptionService.retrieve(this.params).then(function(data) {
                        me.result = data.data;
                    });
                },
                page: function(idx, page) {
                    this.params.firstResult = this.params.firstResult + page * this.params.maxResults;
                    var me = this;
                    descriptionService.retrieve(this.params).then(function(data) {
                        me.result = data.data;
                    });
                }
            },
            isValid: function() {
                return me.isValid(this);
            },
            addExercise: function (description, exercise) {
                this.exercises.push(exerciseService.get(description, this.form, '' + this.index + this.childIndex++, exercise));
                var reduceDescriptions = []
                for (var i = 0; i < this.descriptions.result.result.length; i++) {
                    if (description.id !== this.descriptions.result.result[i].id) {
                        reduceDescriptions.push(this.descriptions.result.result[i]);
                    } else {
                        this.descriptions.params.excludeId.push(description.id);
                    }
                }
                this.descriptions.result.result = reduceDescriptions;

            },
            removeExercise:  function(idx) {
                for(var i = 0; this.exercises.length; i++) {
                    if (i === idx) {
                        var removed = this.exercises.splice(i, 1)[0];
                        for (var j = 0; j < this.descriptions.params.excludeId.length; j++) {
                            if (removed.description.id === this.descriptions.params.excludeId[j]) {
                                this.descriptions.params.excludeId.splice(j, 1);
                            }
                        }
                        break;
                    }
                }
            }
        }
        if (workout) {
            for(var i = 0; i < workout.exercises.length; i++) {
                result.addExercise(workout.exercises[i].description, workout.exercises[i]);
            }
        }
        return result;
    }
});