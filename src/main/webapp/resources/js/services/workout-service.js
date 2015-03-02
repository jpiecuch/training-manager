app.service('workoutService', function($q, $http, exerciseService, urlService, descriptionService, dictionaryService) {
    var FORM_INPUT_MUSCLE = "workoutMuscles";

    var weekDaysMap = {
        0: 'SUNDAY', 1: 'MONDAY', 2: 'TUESDAY', 3: 'WEDNESDAY', 4: 'THURSDAY', 5: 'FRIDAY', 6: 'SATURDAY',
        SUNDAY: 0, MONDAY: 1, TUESDAY: 2, WEDNESDAY: 3, THURSDAY: 4, FRIDAY: 5, SATURDAY: 6
    }

    this.isValid = function(workout) {
        var result =  workout.groups.length > 0;
        _.each(workout.groups, function(group) {
            _.each(group.exercises, function(exercise) {
                if (!exerciseService.isValid(exercise)) {
                    result = false;
                }
            });
        });
        var validInputs = this.isValidInputs(workout);
        workout.valid = result && validInputs;
        return workout.valid;
    }

    this.isValidInputs = function(workout) {
         return workout.form[FORM_INPUT_MUSCLE + workout.index] !== undefined
             && workout.form[FORM_INPUT_MUSCLE + workout.index].$touched
             && workout.form[FORM_INPUT_MUSCLE + workout.index].$valid;
    };

    this.payload = function(workout) {
        var muscles = [];
        for(var i =0; i < workout.muscles.length; i++) {
            var muscle = workout.muscles[i];
            if (muscle.selected) {
                muscles.push(muscle.name);
            }
        }
        var payload = {weekDay: weekDaysMap[workout.weekDay], muscles: muscles, position: 1, phaseId: workout.phaseId, id: workout.id, groups: []};
        _.each(workout.groups, function(group) {
            var payloadGroup = {id: group.id, exercises: []};
            _.each(group.exercises, function(exercise) {
                exercise.group = group.id;
                payloadGroup.exercises.push(exerciseService.payload(exercise));
            });
            payload.groups.push(payloadGroup);
        });
        return payload;
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
                groups: [],
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
                updateSuperSetPositions: function() {
                    _.sortBy(this.groups, 'id');
                    var position = 1;
                    _.each(this.groups, function(item) {
                        if (item.exercises.length > 1) {
                            item.position = position++;
                        }
                    });
                },
                updateSuperSetIds: function() {
                    _.sortBy(this.groups, 'id');
                    var id = 1;
                    _.each(this.groups, function(item) {
                        item.id = id++;
                    });
                },
                removeFromSuperSet: function(group, exercise) {
                    group.removeExercise(exercise);
                    group.updateExercisePositions();
                    _.each(this.groups, function(item) {
                        if (item.id > group.id) {
                            item.id++;
                        }
                    });
                    exercise.position = 1;

                    this.addGroup(group.id + 1, [exercise]);
                    this.updateSuperSetPositions();
                },
                addGroup: function(id, exercises) {
                    var group = {id: id, exercises: exercises};
                    group.updateExercisePositions = function() {
                        _.sortBy(this.exercises, 'position');
                        var position = 1;

                        _.each(this.exercises, function(item) {
                            item.position = position++;
                        });
                    };
                    group.removeExercise = function(exercise) {
                        this.exercises = _.filter(this.exercises, function(item) {
                            return item.position !== exercise.position;
                        });
                    }
                    this.groups.push(group);
                },
                joinToSuperSet: function(newGroup, currentGroup, exercise) {
                    currentGroup.removeExercise(exercise);
                    currentGroup.updateExercisePositions();

                    exercise.position = 999;
                    newGroup.exercises.push(exercise);
                    newGroup.updateExercisePositions();

                    if (currentGroup.exercises.length === 0) {
                        this.groups = _.filter(this.groups, function(item) {
                            return item.id !== currentGroup.id;
                        });
                        this.updateSuperSetIds();
                    }
                    this.updateSuperSetPositions();

                },
                addExercise: function (description, exercise) {
                    var e = exerciseService.get(description, this.form, '' + this.index + this.childIndex++, exercise);
                    e.position = 1;
                    this.addGroup(this.groups.length + 1, [e]);
                    this.updateSuperSetPositions();
                    var reduceDescriptions = [];
                    for (var i = 0; i < this.descriptions.result.result.length; i++) {
                        if (description.id !== this.descriptions.result.result[i].id) {
                            reduceDescriptions.push(this.descriptions.result.result[i]);
                        } else {
                            this.descriptions.params.excludeId.push(description.id);
                        }
                    }
                    this.descriptions.result.result = reduceDescriptions;
                },
                removeExercise:  function(group, exercise) {
                    group.removeExercise(exercise);
                    group.updateExercisePositions();
                    if (group.exercises.length === 0) {
                        this.groups = _.filter(this.groups, function(item) {
                            return item.id !== group.id;
                        });
                        this.updateSuperSetIds();
                        this.updateSuperSetPositions();
                    }
                    this.descriptions.params.excludeId = _.filter(this.descriptions.params.excludeId, function(item) {
                        return item !== exercise.description.id;
                    });
                }
            };
            if (workout) {
                _.each(workout.groups, function(group) {
                    var exercises = [];
                    _.each(group.exercises, function(exercise) {
                        exercises.push(exerciseService.get(exercise.description, result.form, '' + result.index + result.childIndex++, exercise));
                        result.descriptions.params.excludeId.push(exercise.description.id);
                    });
                    result.addGroup(group.id, exercises);
                });
            }
            return result;
        });
    }
});