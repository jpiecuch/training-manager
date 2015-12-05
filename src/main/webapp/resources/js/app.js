var app = angular.module("app", [
    "ui.router", 
    "ui.bootstrap", 
    "oc.lazyLoad",  
    "ngSanitize",
    "pascalprecht.translate",
    "ngCookies",
    'toaster',
    'textAngular',
    'ui.calendar',
    'checklist-model',
    'youtube-embed',
    'tmh.dynamicLocale',
    'chart.js'
]);

app.config(['$animateProvider',
    function($animateProvider) {
        $animateProvider.classNameFilter(/has-animate/);
    }
]);

app.run(function ($rootScope, $location, $state, authenticateService, user, lang, tmhDynamicLocale, dictionaryService) {

    if (user != null && user.authorities) {
        user.authorities = user.authorities.replace('[', '').replace(']', '');
        user.authorities = user.authorities.split(', ');
        dictionaryService.feed([1,2,3,4,5,6,7,8,9,10,11,12]);
    }

    $rootScope.settings = {
        isUserSignIn: user != null,
        user: user,
        lang: lang,
        hasPermission: function(permission) {
            return this.user && _.contains(this.user.authorities, permission);
        }
    };

    tmhDynamicLocale.set(lang);

    $rootScope.$on('$stateChangeStart', function (ev, to, toParams, from) {

        if (to.name !== 'login' && to.name !== 'activate' && !$rootScope.settings.isUserSignIn) {
            $state.transitionTo('login');
            ev.preventDefault();
            $rootScope.settings.isAccessDenied = true;
        } else if (to.name === 'login' && $rootScope.settings.isUserSignIn) {
            $state.transitionTo(from.name === '' ? 'index' : from.name);
            ev.preventDefault();
            $rootScope.settings.isAccessDenied = true;
        }
    });

});

/* Setup Layout Part - Sidebar */
app.controller('SidebarController', ['$scope', '$location', function($scope, $location) {
    $scope.$on('collapseSidebar', function() {
        $scope.sidebar.collapsed = !$scope.sidebar.collapsed;
    });

    $scope.sidebar = {
        collapsed: false,
        elements: [
            {name: 'dashboard', href: '/', icon: 'icon-home'},
            {name: 'plans', collapsed: true, icon: 'fa fa-cogs', children: [{name:'view', href: '/plans'}, {name:'create', href: '/plan'}]},
            {name: 'descriptions', collapsed: true, icon: 'fa fa-folder-open-o', children: [{name:'view', href: '/descriptions'}, {name:'create', href: '/description'}]},
            {name: 'equipments', collapsed: true, icon: 'fa fa-wrench', children: [{name:'view', href: '/equipments'}, {name:'create', href: '/equipment'}]},
            {name: 'calendar', href: '/calendar', icon: 'fa fa-calendar'}
        ],
        isActive: function(element) {
            if (element.children) {
                for (var i = 0; i < element.children.length; i++) {
                    var el = element.children[i];
                    if ($location.url() === el.href){
                        return true
                    }
                }
            } else {
                return $location.url() === element.href;
            }
            return false;
        },
        collapse: function(element) {
            if (element.collapsed !== undefined) {
                element.collapsed = !element.collapsed;
                if (!element.collapsed) {
                    angular.forEach(this.elements, function(item) {
                        if (item.name !== element.name && item.children) {
                            item.collapsed = true;
                        }
                    });
                }
            } else {
                $location.url(element.href);
            }
        }
    }
}]);

/* Setup Layout Part - Quick Sidebar */
app.controller('QuickSidebarController', ['$scope', function($scope) {

}]);

/* Setup Layout Part - Theme Panel */
app.controller('ThemePanelController', ['$scope', function($scope) {
}]);

/* Setup Layout Part - Footer */
app.controller('FooterController', ['$scope', function($scope) {

}]);


app.config(function($translateProvider, contextPath, lang) {
    $translateProvider.preferredLanguage(lang);
    $translateProvider.useStaticFilesLoader({
        prefix: contextPath + '/api/v1/languages/',
        suffix: ''
    });
});
app.service('anonymousInterceptor', function ($q, $location, alertService) {
    return {
        responseError: function(response) {
            if (response.status === 401) {
                $location.path('/login');
            } else if (response.status === 403) {
                alertService.show({type: 'warning', title: 'warning', description: response.data.message});
            }
            return $q.reject(response);
        }
    };
});
app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('anonymousInterceptor');
});

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
app.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        cssFilesInsertBefore: 'ng_load_plugins_before' // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
    });
}]);

/* Setup global settings */
app.factory('settings', ['$rootScope', function($rootScope) {
    // supported languages

    $rootScope.settings.layout = {
        pageSidebarClosed: false, // sidebar menu state
        pageBodySolid: false, // solid body color state
        pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
    };

    return $rootScope.settings;
}]);

/* Setup App Main Controller */
app.controller('AppController', ['$scope', '$rootScope', function($scope, $rootScope) {
}]);

/***
Layout Partials.
By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial 
initialization can be disabled and Layout.init() should be called on page load complete as explained above.
***/

/* Setup Layout Part - Header */
app.controller('HeaderController', ['$scope', 'authenticateService', '$state', '$rootScope', function($scope, authenticateService, $state, $rootScope) {
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
        },
        collapseSidebar: function() {
            $rootScope.$broadcast('collapseSidebar');
        }
    }
}]);

/* Setup Rounting For All Pages */
app.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
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
                        name: 'app',
                        files: [
                            'resources/assets/admin/pages/css/login.css',
                            'resources/js/controllers/LoginController.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/js/services/register-service.js',
                            'resources/js/controllers/RegisterController.js',
                            'resources/js/services/signIn-service.js',
                            'resources/js/controllers/SignInController.js',
                            'resources/js/services/password-service.js',
                            'resources/js/controllers/PasswordController.js'
                        ]
                    });
                }]
            }
        })
        .state('activate', {
            url: "/activate/:code",
            templateUrl: "resources/views/activate.html",
            data: {pageTitle: 'Activate'},
            controller: "ActivateController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/ActivateController.js',
                            'resources/js/services/activate-service.js',
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
                        name: 'app',
                        files: [
                            'resources/assets/global/plugins/amcharts/serial.js',
                            'resources/assets/global/plugins/amcharts/lang/pl.js',
                            'resources/assets/global/plugins/amcharts/themes/light.js',
                            'resources/js/controllers/DashboardController.js',
                            'resources/assets/admin/pages/css/todo.css',
                            'resources/js/services/user-workout-service.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/js/services/account-record-service.js'
                        ] 
                    });
                }]
            }
        })

        //Plan
        .state('plan', {
            url: "/plan?id",
            templateUrl: "resources/views/plan.html",
            data: {pageTitle: 'Plan'},
            controller: "PlanController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/PlanController.js',
                            'resources/js/services/dictionary-service.js',
                            'resources/js/services/description-service.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/js/services/plan-service.js',
                            'resources/js/services/phase-service.js',
                            'resources/js/services/workout-service.js',
                            'resources/js/services/exercise-service.js',
                            'resources/js/services/plan-starter-service.js',
                            'resources/js/services/input-service.js'
                        ]
                    });
                }]
            }
        })

        .state('description', {
            url: "/description?id",
            templateUrl: "resources/views/description.html",
            data: {pageTitle: 'Description'},
            controller: "DescriptionController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/DescriptionController.js',
                            'resources/js/services/dictionary-service.js',
                            'resources/js/services/description-service.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/js/services/video-provider-service.js'
                        ]
                    });
                }]
            }
        })

        .state('equipment', {
            url: "/equipment?id",
            templateUrl: "resources/views/equipment.html",
            data: {pageTitle: 'Equipment'},
            controller: "EquipmentController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/EquipmentController.js',
                            'resources/js/services/dictionary-service.js',
                            'resources/js/services/equipment-service.js',
                            'resources/js/services/form-validate-service.js'
                        ]
                    });
                }]
            }
        })

        //Plan
        .state('plans', {
            url: "/plans",
            templateUrl: "resources/views/plans.html",
            data: {pageTitle: 'Plans'},
            controller: "PlansController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/PlansController.js',
                            'resources/js/services/table-service.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/js/services/plan-service.js',
                            'resources/js/services/phase-service.js',
                            'resources/js/services/workout-service.js',
                            'resources/js/services/exercise-service.js',
                            'resources/js/services/dictionary-service.js',
                            'resources/js/services/description-service.js',
                            'resources/js/services/input-service.js'
                        ]
                    });
                }]
            }
        })

        .state('descriptions', {
            url: "/descriptions",
            templateUrl: "resources/views/descriptions.html",
            data: {pageTitle: 'Description'},
            controller: "DescriptionsController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/DescriptionsController.js',
                            'resources/js/services/description-service.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/js/services/table-service.js'
                        ]
                    });
                }]
            }
        })

        .state('equipments', {
            url: "/equipments",
            templateUrl: "resources/views/equipments.html",
            data: {pageTitle: 'Equipment'},
            controller: "EquipmentsController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/EquipmentsController.js',
                            'resources/js/services/table-service.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/js/services/equipment-service.js'
                        ]
                    });
                }]
            }
        })

        .state('calendar', {
            url: "/calendar",
            templateUrl: "resources/views/calendar.html",
            data: {pageTitle: 'Calendar'},
            controller: "CalendarController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/CalendarController.js',
                            'resources/js/services/user-workout-service.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/assets/global/plugins/fullcalendar/fullcalendar.js',
                            'resources/assets/global/plugins/fullcalendar/fullcalendar.css'
                        ]
                    });
                }]
            }
        })

        .state('workout', {
            url: "/workout/:id",
            templateUrl: "resources/views/workout.html",
            data: {pageTitle: 'Workout'},
            controller: "WorkoutController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'app',
                        files: [
                            'resources/js/controllers/WorkoutController.js',
                            'resources/js/services/user-workout-service.js',
                            'resources/js/services/form-validate-service.js',
                            'resources/js/services/video-provider-service.js'
                        ]
                    });
                }]
            }
        })
}]);

/* Init global settings and run the app */
app.run(["$rootScope", "settings", "$state", function($rootScope, settings, $state) {
    $rootScope.$state = $state; // state to be accessed from view
}]);
