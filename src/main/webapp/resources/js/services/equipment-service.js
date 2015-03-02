app.service('equipmentService', function(urlService, $http, $q, formValidateService, alertService) {
    var equipment = {
        type: null,
        changeType: function() {
            this.strength = undefined;
            this.length = undefined;
            this.weight = undefined;
            this.config = undefined;
            switch(this.type) {
                case 'BAR':
                    this.strength = null;
                    this.length = null;
                    this.config = {
                        handles: null
                    };
                    break;
                case 'BENCH':
                    this.strength = null;
                    this.length = null;
                    this.config = {
                        height: null
                    };
                    break;
                case 'DUMBBELL':
                    this.strength = null;
                    this.length = null;
                    this.weight = null;
                    this.config = {
                        connectedLoad: null
                    };
                    break;
                case 'LOAD':
                    this.weight = null;
                    break;
                case 'NECK':
                    this.strength = null;
                    this.length = null;
                    this.weight = null;
                    this.config = {
                        connectedLoad: null,
                        type: null
                    };
                    break;
                case 'PRESS':
                    this.strength = null;
                    this.weight = null;
                    this.config = {
                        handles: null
                    };
                    break;
                case 'STAND':
                    this.strength = null;
                    this.config = {
                        levels: null,
                        height: {
                            min:  null,
                            max: null
                        }
                    };
                    break;
            }
        },
        create: function() {
            formValidateService.validate(this.form);
            if (this.form.$valid) {
                $http.post(urlService.apiURL('/equipments?type=' + this.type), {
                    id: this.id,
                    type: this.type,
                    weight: this.weight,
                    length: this.length,
                    strength: this.strength,
                    config: this.config
                }).then(function() {
                    alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                });
            } else {
                alertService.show({type: 'warning', title: 'ERROR', description: 'Something is wrong'});
            }
        }
    }


    this.get = function(form, id) {
        var deferred = $q.defer();
        deferred.resolve();
        var result = angular.extend({}, equipment);
        angular.extend(result, {form: form});
        return deferred.promise.then(function() {
            if (id) {
                return $http.get(urlService.apiURL('/equipments/' + id)).then(function (data) {
                    angular.extend(result, data.data);
                    return result;
                });
            }
            return result;
        });
    }

    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/equipments'), { params: params });
    }
});

