app.service('authenticateService', function($http, urlService, $q, $rootScope) {
    var user = null;

    this.getSocials = function() {
        return $http.get(urlService.apiURL('/dictionary/10'));
    }

    this.resetPassword = function(email) {
        return $http.post(urlService.url('/authentication/reset?email=') + email);
    }

    this.create = function(user) {
        return $http.post(urlService.url('/signon'), user);
    }

    this.signIn = function(credentials) {
        var rememberMe = credentials.rememberMe;
        delete credentials.rememberMe;
        return $http.post(urlService.apiURL('/signin'  + (rememberMe ? '?rememberMe=true' : '')), credentials).then(function(data) {
            if (data.status === 201) {
                $rootScope.settings.isUserSignIn = true;
            }
            return data;
        });
    }

    this.signOut = function() {
        return $http.post(urlService.apiURL('/signout')).then(function() {
            user = null;
            $rootScope.settings.isUserSignIn = false;
        });
    }

    this.signed = function() {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return user !== null ? user : null;
        }).then(function(data) {
            if ($rootScope.settings.isUserSignIn && !data) {
                return $http.get(urlService.apiURL('/signin')).then(function (data) {
                    user = data.data;
                    $rootScope.settings.isUserSignIn = true;
                    return user;
                });
            }
            return $rootScope.settings.isUserSignIn ? user : null;
        });
    }
});