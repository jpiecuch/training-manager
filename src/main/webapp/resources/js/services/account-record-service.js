app.service('accountRecordService', function($http, urlService) {
    this.retrieve = function(params) {
        return $http.get(urlService.apiURL('/accountrecords'), { params: params });
    };
});