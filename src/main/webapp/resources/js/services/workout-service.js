MetronicApp.service('workoutService', function($q, $http, exerciseService, urlService, descriptionService, dictionaryService) {
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
            var muscles = [];
            for(var i =0; i < workout.muscles.length; i++) {
                var muscle = workout.muscles[i];
                if (muscle.selected) {
                    muscles.push(muscle.name);
                }
            }
            return $http.post(urlService.apiURL('/workouts'), {weekDay: weekDaysMap[workout.weekDay], muscles: muscles, position: 1, phaseId: workout.phaseId, id: workout.id}).then(function(data) {
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
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return dictionaryService.retrieve(2).then(function(data) {
                return data;
            });
        }).then(function(data) {
            var muscles = [];
            for(var i = 0; i < data.length; i++) {
                muscles.push({name: data[i], selected: workout && _.contains(workout.muscles, data[i])});
            }
            var result = {
                id: workout ? workout.id : undefined,
                index: index,
                childIndex: 0,
                form: form,
                muscles: muscles,
                exercises: [],
                groupIndex: -1,
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
                musclesSelected: function() {
                    for (var i =0; i < this.muscles.length; i++) {
                        if (this.muscles[i].selected) {
                            return true;
                        }
                    }
                    return false;
                },
                isValid: function() {
                    return me.isValid(this);
                },
                isInSuperSet: function(exercise) {
                    for(var j = 0; j < this.exercises.length; j++) {
                        var temp = this.exercises[j];
                        if (exercise.position !== temp.position && exercise.group === temp.group) {
                            return true;
                        }
                    }
                },
                isFirstInSuperSet: function(exercise) {
                    for(var j = 0; j < this.exercises.length; j++) {
                        var temp = this.exercises[j];
                        if (exercise.position !== temp.position && exercise.group === temp.group && exercise.position < temp.position) {
                            return true;
                        }
                    }
                },
                removeFromSuperSet: function(exercise) {
                    var updateGroup = false;
                    for(var j = 0; j < this.exercises.length; j++) {
                        var temp = this.exercises[j];
                        if (exercise.group !== temp.group && updateGroup) {
                            temp.group++;
                        }
                        if (exercise.position !== temp.position && exercise.group === temp.group) {
                            if (exercise.position < temp.position) {
                                temp.group++;
                            } else {
                                exercise.group++;
                            }
                            updateGroup = true;
                        }
                    }
                },
                joinToSuperSet: function(position1, position2) {
                    var exercise1 = this.exercises[position1 - 1];
                    var exercise2 = this.exercises[position2 - 1];
                    this.exercises.splice(exercise2.position  - 1, 1);
                    this.exercises.splice(exercise1.position > exercise2.position  - 1 ? exercise1.position - 1 : exercise1.position , 0, exercise2);
                    exercise2.group = exercise1.group;
                    for(var j = 0; j < this.exercises.length; j++) {
                        this.exercises[j].position = j+1;
                    }
                },
                addExercise: function (description, exercise) {
                    var e = exerciseService.get(description, this.form, '' + this.index + this.childIndex++, exercise);
                    if (!e.position) {
                        e.position = this.exercises.length + 1;
                        e.group = ++this.groupIndex;
                    } else if (e.group > this.groupIndex){
                        this.groupIndex = e.group;
                    }
                    this.exercises.push(e);
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
        });
    }
});