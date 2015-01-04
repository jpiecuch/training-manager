MetronicApp.service('dictionaryService', function(urlService, $http) {
    this.retrieve = function(id) {
        return $http.get(urlService.apiURL('/dictionary/' + id));
    }
});

