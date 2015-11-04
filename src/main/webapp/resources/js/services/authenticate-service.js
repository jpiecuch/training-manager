app.service('authenticateService', function($http, urlService, $q, $rootScope, dictionaryService) {
    var user = null;

    this.getSocials = function() {
        return $http.get(urlService.apiURL('/dictionary/10'));
    };

    this.resetPassword = function(email) {
        return $http.post(urlService.apiURL('/reset'), email);
    };

    this.create = function(user) {
        return $http.post(urlService.url('/signon'), user);
    };

    this.signIn = function(credentials) {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            var rememberMe = credentials.rememberMe;
            return $http.post(urlService.apiURL('/signin' + (rememberMe ? '?rememberMe=true' : '')), credentials).then(function (data) {
                if (data.status === 201) {
                    $rootScope.settings.isUserSignIn = true;
                    delete credentials.rememberMe;
                    dictionaryService.feed([1,2,3,4,5,6,7,8,9,10,11,12]);
                }
                return data;
            });
        }).then(function(data) {
            if (data.status === 201) {
                return $http.get(urlService.apiURL('/signin')).then(function (user) {
                    $rootScope.settings.user = user.data;
                    return data;
                });
            }
            return data;
        });
    };

    this.signOut = function() {
        return $http.post(urlService.apiURL('/signout')).then(function() {
            user = null;
            $rootScope.settings.isUserSignIn = false;
            $rootScope.settings.user = null;
        });
    };

    this.signed = function() {
        return $rootScope.settings.user;
    }
});