app.service('accountRecordService', function($http, urlService, authenticateService) {
    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/users/' + authenticateService.signed().id +'/records'), { params: params });
    };
});