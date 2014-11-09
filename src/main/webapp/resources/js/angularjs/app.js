var training = angular.module('training', ['ui.bootstrap', 'ui.calendar', 'ui.perfect.scrollbar', 'ngDragDrop', 
    'pascalprecht.translate', 'ngCookies', 'ngTable', 'backstretch',  'textAngular', 'ngRoute', 'mgcrea.ngStrap.modal', 'mgcrea.ngStrap.alert', 'ngAnimate'])
    .config(function($translateProvider, contextPath, lang) {
        $translateProvider.preferredLanguage(lang);
        $translateProvider.useStaticFilesLoader({
            prefix: contextPath + '/api/v1/dictionary/language/',
            suffix: ''
        });
        $translateProvider.useCookieStorage();

    }).config(function($routeProvider) {
        $routeProvider.when('/', {
            templateUrl : 'resources/pages/dashboard.html',
            controller  : 'DashboardCtrl'
        }).when('/calendar', {
            templateUrl : 'resources/pages/calendar.html',
            controller  : 'CalendarCtrl'
        }).when('/calendar/day/:date?', {
            templateUrl : 'resources/pages/day.html',
            controller  : 'RecordsCtrl',
            reloadOnSearch: false
        }).when('/exercises', {
            templateUrl : 'resources/pages/exercises.html',
            controller  : 'ExercisesCtrl',
            reloadOnSearch: false
        }).when('/plan', {
            templateUrl : 'resources/pages/plan.html',
            controller  : 'PlanCtrl',
            reloadOnSearch: false
        });
    }).constant('alertOptions', { placement: 'top-right',show: false, duration: 3, dismissable: true, show: true });