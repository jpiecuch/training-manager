MetronicApp.service('descriptionService', function(urlService, $http, $q, formValidateService, alertService, ngTableParams) {

    var description = {
        names: { pl: null, en: null },
        movieUrl: null,
        description: null,
        muscles: null,
        type: null,
        equipment: null,
        level: null,
        mechanics: null,
        force: null,
        form: null,
        create: function() {
            formValidateService.validate(this.form);
            if (this.form.$valid) {
                $http.post(urlService.apiURL('/descriptions/'), {
                    id: this.id,
                    names: this.names,
                    movieUrl: this.movieUrl,
                    description: this.description,
                    muscles: this.muscles,
                    type: this.type,
                    equipment: this.equipment,
                    level: this.level,
                    mechanics: this.mechanics,
                    force: this.force
                }).then(function() {
                    alertService.show({type: 'success', title: 'OK', description: 'Submit'});
                });
            } else {
                alertService.show({type: 'warning', title: 'ERROR', description: 'Something is wrong'});
            }
        }
    }

    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/descriptions'), { params: params });
    }

    this.get = function(form, id) {
        var deferred = $q.defer();
        deferred.resolve();
        var result = angular.extend({}, description);
        angular.extend(result, {form: form});
        return deferred.promise.then(function() {
            if (id) {
                return $http.get(urlService.apiURL('/descriptions/' + id)).then(function (data) {
                    angular.extend(result, data.data);
                    return result;
                });
            }
            return result;
        });
    }
});

