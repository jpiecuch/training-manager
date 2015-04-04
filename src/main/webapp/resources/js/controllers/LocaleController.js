'use strict';

app.controller('LocaleController', function($scope, $http, lang, $translate, urlService) {
    $scope.locale = {
        langs: ['pl', 'en'],
        current: null,
        update: function(lang) {
            var me = this;
            $http.post(urlService.apiURL('/locale'), lang).success(function() {
                me.current = lang;
                $translate.use(lang);
            });
        },
        init: function() {
            $scope.locale.current = angular.copy(lang);
        }
    }
});