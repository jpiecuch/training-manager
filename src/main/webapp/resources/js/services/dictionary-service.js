app.service('dictionaryService', function(urlService, $http, $q) {

    var map = {};

    this.retrieve = function(id) {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return map[id];
        }).then(function(data) {
            return data ? data : $http.get(urlService.apiURL('/dictionary/' + id)).then(function(data) {
                map[id] = data.data;
                return map[id];
            });
        });
    }
});

