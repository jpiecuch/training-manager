'use strict';

app.controller('LocaleController', function($scope, $http, lang, $translate, urlService, tmhDynamicLocale, $rootScope) {
    $scope.locale = {
        langs: ['pl', 'en'],
        current: null,
        update: function(lang) {
            var me = this;
            $http.post(urlService.apiURL('/locale'), lang).success(function() {
                me.current = lang;
                $translate.use(lang);
                tmhDynamicLocale.set(lang).then(function() {
                    $rootScope.$broadcast('changeLang', {lang: lang});
                });

            });
        },
        init: function() {
            $scope.locale.current = angular.copy(lang);
        }
    }
});