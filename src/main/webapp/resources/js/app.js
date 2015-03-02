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
    'checklist-model'
]);

app.config(['$animateProvider',
    function($animateProvider) {
        $animateProvider.classNameFilter(/has-animate/);
    }
])

app.run(function ($rootScope, $location, $state, authenticateService, isSignIn) {
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

app.config(function($translateProvider, contextPath, lang) {
    $translateProvider.preferredLanguage(lang);
    $translateProvider.useStaticFilesLoader({
        prefix: contextPath + '/api/v1/languages/',
        suffix: ''
    });
    $translateProvider.useCookieStorage();
});

app.config(function ($httpProvider) {

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
    }

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
        }
    }
}]);

/* Setup Layout Part - Sidebar */
app.controller('SidebarController', ['$scope', function($scope) {

}]);

/* Setup Layout Part - Quick Sidebar */
app.controller('QuickSidebarController', ['$scope', function($scope) {    

}]);

/* Setup Layout Part - Theme Panel */
app.controller('ThemePanelController', ['$scope', function($scope) {    
;
}]);

/* Setup Layout Part - Footer */
app.controller('FooterController', ['$scope', function($scope) {

}]);

app.controller('SignOutController', ['$scope', function($scope) {

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
                        name: 'app',
                        files: [
                             'resources/js/controllers/DashboardController.js'
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
                            'resources/js/services/plan-starter-service.js'
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
                            'resources/js/services/form-validate-service.js',
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
                            'resources/assets/global/plugins/fullcalendar/fullcalendar.js',
                            'resources/assets/global/plugins/fullcalendar/fullcalendar.css',
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
                            'resources/js/services/form-validate-service.js'
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

/*
var angulardecimals = angular.module("angular-decimals", []);
angulardecimals.directive("decimals", function ($filter) {
    return {
        restrict: "A", // Only usable as an attribute of another HTML element
        require: "?ngModel",
        scope: {
            decimals: "@",
            decimalPoint: "@"
        },
        link: function (scope, element, attr, ngModel) {
            var decimalCount = parseInt(scope.decimals) || 2;
            var decimalPoint = scope.decimalPoint || ".";

            // Run when the model is first rendered and when the model is changed from code
            ngModel.$render = function() {
                if (ngModel.$modelValue != null && ngModel.$modelValue >= 0) {
                    if (typeof decimalCount === "number") {
                        element.val(ngModel.$modelValue.toFixed(decimalCount).toString().replace(".", ","));
                    } else {
                        element.val(ngModel.$modelValue.toString().replace(".", ","));
                    }
                }
            }

            // Run when the view value changes - after each keypress
            // The returned value is then written to the model
            ngModel.$parsers.unshift(function(newValue) {
                if (typeof decimalCount === "number") {
                    var floatValue = parseFloat(newValue.replace(",", "."));
                    if (decimalCount === 0) {
                        return parseInt(floatValue);
                    }
                    return parseFloat(floatValue.toFixed(decimalCount));
                }

                return parseFloat(newValue.replace(",", "."));
            });

            // Formats the displayed value when the input field loses focus
            element.on("change", function(e) {
                var floatValue = parseFloat(element.val().replace(",", "."));
                if (!isNaN(floatValue) && typeof decimalCount === "number") {
                    if (decimalCount === 0) {
                        element.val(parseInt(floatValue));
                    } else {
                        var strValue = floatValue.toFixed(decimalCount);
                        element.val(strValue.replace(".", decimalPoint));
                    }
                }
            });
        }
    }
});*/
