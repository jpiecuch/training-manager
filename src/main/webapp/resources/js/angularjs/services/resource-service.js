training.service('resourceService', function($http, urlService) {
    this.getAll = function(path) {
        return $http.get(urlService.url(path));
    }


});