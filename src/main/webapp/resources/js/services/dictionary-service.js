app.service('dictionaryService', function(urlService, $http, $q) {

    var map = {};

    this.retrieve = function(id) {
        var deferred = $q.defer();
        deferred.resolve();
        var me = this;
        return deferred.promise.then(function() {
            return me.get(id);
        }).then(function(data) {
            return data ? data : $http.get(urlService.apiURL('/dictionary/' + id)).then(function(data) {
                map[id] = data.data;
                return map[id];
            });
        });
    };

    this.get = function(id) {
        return map[id];
    };

    this.feed = function(ids) {
        $http.get(urlService.apiURL('/dictionary/'), {params: {id : ids}}).then(function(data) {
            angular.extend(map, data.data);
        });
    };
});

