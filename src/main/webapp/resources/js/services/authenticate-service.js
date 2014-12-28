MetronicApp.service('authenticateService', function($http, urlService, $q, $rootScope) {
    var user = null;

    this.getSocials = function() {
        return $http.get(urlService.apiURL('/dictionary/social'));
    }

    this.resetPassword = function(email) {
        return $http.post(urlService.url('/authentication/reset?email=') + email);
    }

    this.create = function(user) {
        return $http.post(urlService.url('/authentication/create'), user);
    }

    this.signIn = function(credentials) {
        return $http.post(urlService.apiURL('/signin'), credentials).then(function(data) {
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
        return user;
    }

    this.isSignedIn = function() {
        var deferred = $q.defer();
        deferred.resolve();
        return deferred.promise.then(function() {
            return user !== null
        }).then(function(data) {
            if (!data) {
                return $http.get(urlService.apiURL('/signin')).then(function (data) {

                    if (data.data.entity) {
                        user = data.data.entity;
                        $rootScope.settings.login = true;
                        return true;
                    }
                    $rootScope.settings.login = false;
                    return false;
                });
            }
            return true;
        });
    }
});