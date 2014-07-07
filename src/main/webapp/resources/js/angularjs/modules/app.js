var training = angular.module('training', ['ui.bootstrap', 'ui.calendar', 'ui.perfect.scrollbar', 'ngDragDrop', 
    'pascalprecht.translate', 'ngCookies', 'angular-growl', 'ngTable', 'backstretch', 'youtube-embed'])
        .config(function($translateProvider, contextPath) {
            $translateProvider.useStaticFilesLoader({
                prefix: contextPath + '/api/languages/',
                suffix: ''
            });
            $translateProvider.useLocalStorage();
            $translateProvider.preferredLanguage('pl');
        });