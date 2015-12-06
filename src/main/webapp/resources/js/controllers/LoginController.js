'use strict';

app.controller('LoginController', function($sce, $scope, urlService, authenticateService, policyService) {
    $scope.login = {
        tab: {
            idx: 0,
            setIdx: function(idx) {
                this.idx = idx;
            }
        },
        social: {
            providers:  [],
            getUrl: function(id) {
                return $sce.trustAsResourceUrl(urlService.url('signin') + '/' + id);
            }
        }
    };

    $scope.init = function() {
        authenticateService.getSocials().then(function(data) {
            $scope.login.social.providers = data.data;
        });
    };
});