app.service('planService', function($q, $http, phaseService, formValidateService, alertService, urlService, inputService) {
    this.submit = function(type, plan) {
        var payload = {name: plan.name.value, goal: plan.goal.value, id: plan.id, creatorId: plan.creatorId, phases: []};
        _.each(plan.phases, function(phase) {
            payload.phases.push(phaseService.payload(phase));
        });
        return $http[type](urlService.apiURL('/plans/' + (type === 'put' ? plan.id : '')), payload).then(function(data) {
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

    this.removePhase = function(plan, idx) {
        plan.phases.splice(idx, 1);
        plan.phase = plan.phases[0];
    };

    function rec(o, s) {
        s = s.replace(/\[(\w+)\]/g, '.$1'); // convert indexes to properties
        s = s.replace(/^\./, '');           // strip a leading dot
        var a = s.split('.');
        for (var i = 0, n = a.length; i < n; ++i) {
            var k = a[i];
            if (k in o) {
                o = o[k];
            } else {
                return;
            }
        }
        return o;
    }

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
                    me.submit(this.id ? 'put' : 'post', this).then(function () {
                        alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                    });
                },
                addPhase: function(phase) {
                    me.addPhase(this, phase);
                },
                removePhase:  function(idx) {
                    me.removePhase(this, idx);
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
    }

    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/plans'), { params: params });
    }
});