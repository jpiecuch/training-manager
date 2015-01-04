MetronicApp.service('descriptionService', function(urlService, $http) {
    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/descriptions/'), { params: params });
    }
});

