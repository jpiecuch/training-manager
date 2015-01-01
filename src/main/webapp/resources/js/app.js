/***
Metronic AngularJS App Main Script
***/

/* Metronic App */
var MetronicApp = angular.module("MetronicApp", [
    "ui.router", 
    "ui.bootstrap", 
    "oc.lazyLoad",  
    "ngSanitize",
    "pascalprecht.translate",
    "ngCookies"
]);

MetronicApp.run(function ($rootScope, $location, $state, authenticateService, isSignIn) {
    $rootScope.settings = {
        isUserSignIn: isSignIn
    }

    // enumerate routes that don't need authentication
    var routesThatDontRequireAuth = ['/'];

    // check if current location matches route
    var routeClean = function (route) {
        return _.find(routesThatDontRequireAuth,
            function (noAuthRoute) {
                return _.str.startsWith(route, noAuthRoute);
            });
    };

    $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from, fromParams) {
        // if route requires auth and user is not logged in
        if (to.name !== 'login' && !$rootScope.settings.isUserSignIn) {
            $state.transitionTo('login');
            ev.preventDefault();
            $rootScope.settings.isAccessDenied = true;
        } else if (to.name === 'login' && $rootScope.settings.isUserSignIn) {
            $state.transitionTo(from.name);
            ev.preventDefault();
            $rootScope.settings.isAccessDenied = true;
        }
    });

});

MetronicApp.config(function($translateProvider, contextPath, lang) {
    $translateProvider.preferredLanguage(lang);
    $translateProvider.useStaticFilesLoader({
        prefix: contextPath + '/api/v1/languages/',
        suffix: ''
    });
    $translateProvider.useCookieStorage();
});

MetronicApp.config(function ($httpProvider) {

    var logsOutUserOn401 = ['$q', '$location', function ($q, $location) {
        var success = function (response) {
            return response;
        };

        var error = function (response) {
            if (response.status === 401) {
                //redirect them back to login page
                $location.path('/');

                return $q.reject(response);
            }
            else {
                return $q.reject(response);
            }
        };

        return function (promise) {
            return promise.then(success, error);
        };
    }];

    $httpProvider.interceptors.push(logsOutUserOn401);
});

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
MetronicApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        cssFilesInsertBefore: 'ng_load_plugins_before' // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
    });
}]);

/* Setup global settings */
MetronicApp.factory('settings', ['$rootScope', function($rootScope) {
    // supported languages

    $rootScope.settings.layout = {
        pageSidebarClosed: false, // sidebar menu state
        pageBodySolid: false, // solid body color state
        pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
    }

    return $rootScope.settings;
}]);

/* Setup App Main Controller */
MetronicApp.controller('AppController', ['$scope', '$rootScope', function($scope, $rootScope) {

}]);

/***
Layout Partials.
By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial 
initialization can be disabled and Layout.init() should be called on page load complete as explained above.
***/

/* Setup Layout Part - Header */
MetronicApp.controller('HeaderController', ['$scope', 'authenticateService', '$state', '$rootScope', function($scope, authenticateService, $state, $rootScope) {
    $scope.header = {
        signOut: function() {
            authenticateService.signOut().then(function() {
                $state.go('login');
            })
        },
        sidebar: {
            collapse: function() {
                $rootScope.settings.layout.pageSidebarClosed = !$rootScope.settings.layout.pageSidebarClosed;
            }
        }
    }
}]);

/* Setup Layout Part - Sidebar */
MetronicApp.controller('SidebarController', ['$scope', function($scope) {

}]);

/* Setup Layout Part - Quick Sidebar */
MetronicApp.controller('QuickSidebarController', ['$scope', function($scope) {    

}]);

/* Setup Layout Part - Theme Panel */
MetronicApp.controller('ThemePanelController', ['$scope', function($scope) {    
;
}]);

/* Setup Layout Part - Footer */
MetronicApp.controller('FooterController', ['$scope', function($scope) {

}]);

MetronicApp.controller('SignOutController', ['$scope', function($scope) {

}]);

/* Setup Rounting For All Pages */
MetronicApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/");
    
    $stateProvider
        //Login
        .state('login', {
            url: "/login",
            templateUrl: "resources/views/login.html",
            data: {pageTitle: 'Login'},
            controller: "LoginController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        files: [
                            'resources/assets/admin/pages/css/login.css',
                            'resources/js/controllers/LoginController.js'
                        ]
                    });
                }]
            }
        })
        // Index
        .state('index', {
            url: "/",
            templateUrl: "resources/views/dashboard.html",
            data: {pageTitle: 'Admin Dashboard Template'},
            controller: "DashboardController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        files: [
                             'resources/js/controllers/DashboardController.js'
                        ] 
                    });
                }]
            }
        })

        //Plan
        .state('plan', {
            url: "/plan",
            templateUrl: "resources/views/plan.html",
            data: {pageTitle: 'Plan'},
            controller: "PlanController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        files: [
                            'resources/js/controllers/PlanController.js'
                        ]
                    });
                }]
            }
        })
}]);

/* Init global settings and run the app */
MetronicApp.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
}]);