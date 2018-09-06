app.service('executionService', function($q, $http, urlService) {
    this.group = function(params) {
        return $http.get(urlService.apiURL('/executions/group'), { params: params });
    };
});