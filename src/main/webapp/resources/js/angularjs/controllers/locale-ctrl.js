training.controller("LocaleCtrl", function($scope, $translate, $http, urlService, lang, alertService, $alert) {
    $scope.locale = {
       langs: ['pl', 'en'],
       current: null,
       update: function(lang) {
           var me = this;
           $http.get(urlService.apiURL('/config/locale'), {params: {lang: lang}}).success(function(data) {
               me.current = lang;
               $translate.use(me.current);
           });
       },
       init: function() {
           $scope.locale.current = angular.copy(lang);
       }
   }
});