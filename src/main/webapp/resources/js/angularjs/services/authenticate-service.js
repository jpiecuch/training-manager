training.service('authenticateService', function($http, urlService) {
    this.getSocials = function() {
        return $http.get(urlService.apiURL('/dictionary/social'));
    }

    this.resetPassword = function(email) {
        return $http.post(urlService.url('/authentication/reset?email=') + email);
    }

    this.create = function(user) {
        return $http.post(urlService.url('/authentication/create'), user);
    }
});