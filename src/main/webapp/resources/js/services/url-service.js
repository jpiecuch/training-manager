app.service('urlService', function(contextPath, version) {
    this.url = function(suffix) {
        return contextPath + '/' + suffix;
    };

    this.apiURL = function(suffix) {
        return this.url('api/' + version + suffix);
    }


});

