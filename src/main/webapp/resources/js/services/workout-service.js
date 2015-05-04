app.service('workoutService', function($q, $http, exerciseService, urlService, descriptionService, dictionaryService, inputService) {
    var weekDaysMap = {
        0: 'SUNDAY', 1: 'MONDAY', 2: 'TUESDAY', 3: 'WEDNESDAY', 4: 'THURSDAY', 5: 'FRIDAY', 6: 'SATURDAY',
        SUNDAY: 0, MONDAY: 1, TUESDAY: 2, WEDNESDAY: 3, THURSDAY: 4, FRIDAY: 5, SATURDAY: 6
    };

    this.payload = function(workout) {
        var muscles = [];
        for(var i =0; i < workout.muscles.value.length; i++) {
            var muscle = workout.muscles.value[i];
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
    };

    this.activateGroup = function(workout, group) {
        workout.group = group;
    };

    this.muscles = function(workout) {
        var muscles = dictionaryService.get(2);
        var result = [];
        for(var i = 0; i < muscles.length; i++) {
            result.push({name: muscles[i], selected: workout && _.contains(workout.muscles, muscles[i])});
        }
        return result;
    };

    this.addGroup = function(workout, id, exercises) {
        workout.touched = true;
        var group = {
            id: id ? id : workout.groups.length + 1,
            exercises: exercises ? exercises : [],
            touched: false,
            removeExercise: function (exercise) {
                this.touched = true;
                this.exercises = _.filter(this.exercises, function (item) {
                    return item.position !== exercise.position;
                });
                _.sortBy(this.exercises, 'id');
                var position = 1;
                _.each(this.exercises, function (item) {
                    item.position = position++;
                });
            },
            addExercise: function(exercise, descriptions) {
                this.touched = true;
                var e = exerciseService.get(exercise);
                e.position = this.exercises.length + 1;
                this.exercises.push(e);
                var reduceDescriptions = [];
                for (var i = 0; i < descriptions.result.result.length; i++) {
                    if (exercise.description.id !== descriptions.result.result[i].id) {
                        reduceDescriptions.push(descriptions.result.result[i]);
                    } else {
                        descriptions.params.excludeId.push(exercise.description.id);
                    }
                }
                descriptions.result.result = reduceDescriptions;
            },
            hasErrors: function() {
                var hasErrors = undefined;
                for (var i =0; i < this.exercises.length; i++) {
                    if (this.exercises[i].hasErrors()) {
                        hasErrors = true;
                        break;
                    } else if (hasErrors === undefined && this.exercises[i].hasErrors() === false) {
                        hasErrors = false;
                    }
                }
                return hasErrors !== undefined || this.touched ? hasErrors || this.exercises.length === 0 : hasErrors;
            }
        };
        workout.groups.push(group);
        workout.activateGroup(group);
    };

    this.hasErrors = function(workout) {
        var hasErrors = workout.muscles.isValid();

        for (var i = 0; i < workout.groups.length; i++) {
            if (workout.groups[i].hasErrors()) {
                hasErrors = true;
                break;
            } else if (hasErrors === undefined && workout.groups[i].hasErrors() === false) {
                hasErrors = false;
            }
        }
        return hasErrors !== undefined || workout.touched ? hasErrors ||  workout.groups.length === 0 : hasErrors;
    };

    this.get = function(weekDay, workout) {
        var me = this;

        var muscles = this.muscles(workout);
        var result = {
            id: workout ? workout.id : undefined,
            muscles: inputService.getArray(muscles),
            groups: [],
            weekDay: workout ? weekDaysMap[workout.weekDay] : weekDay,
            descriptions: descriptionService.search(),
            touched: false,
            isValid: function () {
                return me.isValid(this);
            },
            addGroup: function (id, exercises) {
                me.addGroup(this, id, exercises)
            },
            activateGroup: function (group) {
                me.activateGroup(this, group);
            },
            isGroupActive: function (group) {
                return this.group === group;
            },
            hasErrors: function() {
                return me.hasErrors(this);
            }
        };
        if (workout) {
            angular.forEach(workout.groups, function (group) {
                var exercises = [];
                angular.forEach(group.exercises, function (exercise) {
                    exercises.push(exerciseService.get(exercise));
                    result.descriptions.params.excludeId.push(exercise.description.id);
                });
                result.addGroup(group.id, exercises);
            });
            result.activateGroup(result.groups[0]);
        }
        return result;
    }
});