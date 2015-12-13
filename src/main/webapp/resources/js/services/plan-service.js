app.service('planService', function($q, $http, phaseService, formValidateService, alertService, urlService, inputService) {
    var PUT_METHOD = 'put';
    var POST_METHOD = 'post';

    this.submit = function(type, plan) {
        var payload = {name: plan.name.value, goal: plan.goal.value, id: plan.id, creatorId: plan.creatorId, phases: []};
        _.each(plan.phases, function(phase) {
            payload.phases.push(phaseService.payload(phase));
        });
        return $http[type](urlService.apiURL('/plans/' + (type === PUT_METHOD ? plan.id : '')), payload).then(function(data) {
            return data;
        });
    };

    this.activatePhase = function(plan, phase) {
        plan.phase = phase;
    };

    this.addPhase = function(plan, phase) {
        var next = phaseService.get(phase);
        next.position = plan.phases.length + 1;
        plan.phases.push(next);
    };

    this.removePhase = function(plan, o) {
        var idx = plan.phases.indexOf(o);
        plan.phases.splice(idx, 1);
        plan.phase = plan.phases[0];
        var position = 1;
        _.each(plan.phases, function (item) {
            item.position = position++;
        });
    };

    this.isPhaseLast = function(plan, o) {
      return o.position === plan.phases.length;
    };

    this.movePhase = function (plan, o, count) {
        var idx = plan.phases.indexOf(o);
        var newPos = idx + count;
        plan.phases.splice(idx, 1);
        plan.phases.splice(newPos,0,o);
        var position = 1;
        _.each(plan.phases, function (item) {
            item.position = position++;
        });
    };

    this.get = function(id) {
        var me = this;
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return id ? $http.get(urlService.apiURL('/plans/' + id), {params: {full: true}}) : undefined;
        }).then(function(data) {
            var plan = {
                id: data ? data.data.id : undefined,
                creatorId: data ? data.data.creatorId : null,
                name: inputService.get(data ? data.data.name : null),
                goal: inputService.get(data ? data.data.goal : null),
                phases: [],
                editable: data ? data.data.editable : true,
                movePhase: function (idx, count) {
                    me.movePhase(this, idx, count);
                },
                activatePhase: function(phase) {
                    me.activatePhase(this, phase);
                },
                isActive: function(phase) {
                    return this.phase == phase;
                },
                isValid: function() {
                    return me.isValid(this);
                },
                submit: function() {
                    var plan = this;
                    var method = plan.id ? PUT_METHOD : POST_METHOD;
                    me.submit(method, this).then(function (data) {
                        if (method === POST_METHOD) {
                            plan.id = data.data;
                        }
                        alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                    });
                },
                addPhase: function(phase) {
                    me.addPhase(this, phase);
                },
                removePhase:  function(idx) {
                    me.removePhase(this, idx);
                },
                isPhaseLast: function(phase) {
                    return me.isPhaseLast(this, phase);
                }
            };
            if (data) {
                for(var i = 0; i < data.data.phases.length; i++) {
                    plan.addPhase(data.data.phases[i]);
                }
                plan.phase = plan.phases[0];
            }
            return plan;
        });
    };

    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/plans'), { params: params });
    };

    this.delete = function(id) {
        return $http.delete(urlService.apiURL('/plans/' + id));
    }
});