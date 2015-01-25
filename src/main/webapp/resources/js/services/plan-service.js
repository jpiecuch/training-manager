MetronicApp.service('planService', function($q, $http, phaseService, formValidateService, alertService, urlService) {
    var FORM_INPUT_NAME = "planName";
    var FORM_INPUT_GOAL = "planGoal";

    this.isValid = function(plan) {
        var result = plan.phases.length > 0;
        for (var i = 0; i < plan.phases.length; i++) {
            var phase = plan.phases[i];
            if (!phaseService.isValid(phase)) {
                result = false;
            }
        }
        plan.valid = result && this.isValidInputs(plan);
        return plan.valid;
    }

    this.isValidInputs = function(plan) {
        return plan.form[FORM_INPUT_NAME] !== undefined
            && plan.form[FORM_INPUT_NAME].$touched
            && plan.form[FORM_INPUT_NAME].$valid
            && plan.form[FORM_INPUT_GOAL] !== undefined
            && plan.form[FORM_INPUT_GOAL].$touched
            && plan.form[FORM_INPUT_GOAL].$valid
    }

    this.post = function(plan) {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return $http.post(urlService.apiURL('/plans'),  {name: plan.name, goal: plan.goal, id: plan.id, creatorId: plan.creatorId}).then(function(data) {
                plan.id = data.data;
                return data;
            });
        }).then(function(data) {
            var array = [];
            for (var i =0; i < plan.phases.length; i++) {
                var phase =  plan.phases[i];
                phase.planId = data.data;
                phase.position = i + 1;
                array.push(phaseService.post(phase));
            }
            return $q.all(array);
        });
    }

    this.get = function(form, index, id) {
        var me = this;
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return id ? $http.get(urlService.apiURL('/plans/' + id), {params: {full: true}}) : undefined;
        }).then(function(data) {
            var plan = {
                id: data ? data.data.id : undefined,
                form: form,
                index: index,
                childIndex: 0,
                creatorId: data ? data.data.creatorId : null,
                name: data ? data.data.name : null,
                goal: data ? data.data.goal : null,
                phases: [],
                isValid: function() {
                    return me.isValid(this);
                },
                create: function(form) {

                    formValidateService.validate(form);
                    if (this.isValid()) {
                        me.post(this).then(function() {
                            alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                        });
                    } else {
                        alertService.show({type: 'warning', title: 'ERROR', description: 'Something is wrong'});
                    }
                },
                addPhase: function(phase) {
                    this.phases.push(phaseService.get(this.form, '' + this.index + this.childIndex++, phase));
                },
                removePhase:  function(idx) {
                    for(var i = 0; this.phases.length; i++) {
                        if (i === idx) {
                            this.phases.splice(i, 1);
                            break;
                        }
                    }
                }
            }
            if (data) {
                for(var i = 0; i < data.data.phases.length; i++) {
                    plan.addPhase(data.data.phases[i]);
                }
            }
            return plan;
        });
    }

    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/plans'), { params: params });
    }
});