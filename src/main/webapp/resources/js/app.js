/***
Metronic AngularJS App Main Script
***/

/* Metronic App */
var MetronicApp = angular.module("MetronicApp", [
    "ui.router", 
    "ui.bootstrap", 
    "oc.lazyLoad",  
    "ngSanitize"
]); 

/* Configure ocLazyLoader(refer: https://github.com/ocombe/ocLazyLoad) */
MetronicApp.config(['$ocLazyLoadProvider', function($ocLazyLoadProvider) {
    $ocLazyLoadProvider.config({
        cssFilesInsertBefore: 'ng_load_plugins_before' // load the above css files before a LINK element with this ID. Dynamic CSS files must be loaded between core and theme css files
    });
}]);

/* Setup global settings */
MetronicApp.factory('settings', ['$rootScope', function($rootScope) {
    // supported languages
    var settings = {
        layout: {
            pageSidebarClosed: false, // sidebar menu state
            pageBodySolid: false, // solid body color state
            pageAutoScrollOnLoad: 1000 // auto scroll to top on page load
        },
        layoutImgPath: Metronic.getAssetsPath() + 'admin/layout/img/',
        layoutCssPath: Metronic.getAssetsPath() + 'admin/layout/css/',
        login: false
    };

    $rootScope.settings = settings;

    return settings;
}]);

/* Setup App Main Controller */
MetronicApp.controller('AppController', ['$scope', '$rootScope', function($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function() {
        Metronic.initComponents(); // init core components
        //Layout.init(); //  Init entire layout(header, footer, sidebar, etc) on page load if the partials included in server side instead of loading with ng-include directive 
    });
}]);

/***
Layout Partials.
By default the partials are loaded through AngularJS ng-include directive. In case they loaded in server side(e.g: PHP include function) then below partial 
initialization can be disabled and Layout.init() should be called on page load complete as explained above.
***/

/* Setup Layout Part - Header */
MetronicApp.controller('HeaderController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initHeader(); // init header
    });
}]);

/* Setup Layout Part - Sidebar */
MetronicApp.controller('SidebarController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initSidebar(); // init sidebar
    });
}]);

/* Setup Layout Part - Quick Sidebar */
MetronicApp.controller('QuickSidebarController', ['$scope', function($scope) {    
    $scope.$on('$includeContentLoaded', function() {
        setTimeout(function(){
            QuickSidebar.init(); // init quick sidebar        
        }, 2000)
    });
}]);

/* Setup Layout Part - Theme Panel */
MetronicApp.controller('ThemePanelController', ['$scope', function($scope) {    
    $scope.$on('$includeContentLoaded', function() {
        Demo.init(); // init theme panel
    });
}]);

/* Setup Layout Part - Footer */
MetronicApp.controller('FooterController', ['$scope', function($scope) {
    $scope.$on('$includeContentLoaded', function() {
        Layout.initFooter(); // init footer
    });
}]);

/* Setup Rounting For All Pages */
MetronicApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {
    // Redirect any unmatched url
    $urlRouterProvider.otherwise("/dashboard.html");
    
    $stateProvider

        // Dashboard
        .state('dashboard', {
            url: "/dashboard.html",
            templateUrl: "resources/views/dashboard.html",
            data: {pageTitle: 'Admin Dashboard Template'},
            controller: "DashboardController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        files: [
                            'resources/assets/global/plugins/morris/morris.css',
                            'resources/assets/admin/pages/css/tasks.css',
                            
                            'resources/assets/global/plugins/morris/morris.min.js',
                            'resources/assets/global/plugins/morris/raphael-min.js',
                            'resources/assets/global/plugins/jquery.sparkline.min.js',

                            'resources/assets/admin/pages/scripts/index3.js',
                            'resources/assets/admin/pages/scripts/tasks.js',

                             'resources/js/controllers/DashboardController.js'
                        ] 
                    });
                }]
            }
        })

        // AngularJS plugins
        .state('fileupload', {
            url: "/file_upload.html",
            templateUrl: "resources/views/file_upload.html",
            data: {pageTitle: 'AngularJS File Upload'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'angularFileUpload',
                        files: [
                            'resources/assets/global/plugins/angularjs/plugins/angular-file-upload/angular-file-upload.min.js'
                        ] 
                    }, {
                        name: 'MetronicApp',
                        files: [
                            'resources/js/controllers/GeneralPageController.js'
                        ]
                    }]);
                }]
            }
        })

        // UI Select
        .state('uiselect', {
            url: "/ui_select.html",
            templateUrl: "resources/views/ui_select.html",
            data: {pageTitle: 'AngularJS Ui Select'},
            controller: "UISelectController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'ui.select',
                        files: [
                            'resources/assets/global/plugins/angularjs/plugins/ui-select/select.min.css',
                            'resources/assets/global/plugins/angularjs/plugins/ui-select/select.min.js'
                        ] 
                    }, {
                        name: 'MetronicApp',
                        files: [
                            'resources/js/controllers/UISelectController.js'
                        ] 
                    }]);
                }]
            }
        })

        // UI Bootstrap
        .state('uibootstrap', {
            url: "/ui_bootstrap.html",
            templateUrl: "resources/views/ui_bootstrap.html",
            data: {pageTitle: 'AngularJS UI Bootstrap'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            'resources/js/controllers/GeneralPageController.js'
                        ] 
                    }]);
                }] 
            }
        })

        // Tree View
        .state('tree', {
            url: "/tree",
            templateUrl: "resources/views/tree.html",
            data: {pageTitle: 'jQuery Tree View'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            'resources/assets/global/plugins/jstree/dist/themes/default/style.min.css',

                            'resources/assets/global/plugins/jstree/dist/jstree.min.js',
                            'resources/assets/admin/pages/scripts/ui-tree.js',
                            'resources/js/controllers/GeneralPageController.js'
                        ] 
                    }]);
                }] 
            }
        })     

        // Form Tools
        .state('formtools', {
            url: "/form-tools",
            templateUrl: "resources/views/form_tools.html",
            data: {pageTitle: 'Form Tools'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            'resources/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css',
                            'resources/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css',
                            'resources/assets/global/plugins/jquery-tags-input/jquery.tagsinput.css',
                            'resources/assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css',
                            'resources/assets/global/plugins/typeahead/typeahead.css',

                            'resources/assets/global/plugins/fuelux/js/spinner.min.js',
                            'resources/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js',
                            'resources/assets/global/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js',
                            'resources/assets/global/plugins/jquery.input-ip-address-control-1.0.min.js',
                            'resources/assets/global/plugins/bootstrap-pwstrength/pwstrength-bootstrap.min.js',
                            'resources/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js',
                            'resources/assets/global/plugins/jquery-tags-input/jquery.tagsinput.min.js',
                            'resources/assets/global/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js',
                            'resources/assets/global/plugins/bootstrap-touchspin/bootstrap.touchspin.js',
                            'resources/assets/global/plugins/typeahead/handlebars.min.js',
                            'resources/assets/global/plugins/typeahead/typeahead.bundle.min.js',
                            'resources/assets/admin/pages/scripts/components-form-tools.js',

                            'resources/js/controllers/GeneralPageController.js'
                        ] 
                    }]);
                }] 
            }
        })        

        // Date & Time Pickers
        .state('pickers', {
            url: "/pickers",
            templateUrl: "resources/views/pickers.html",
            data: {pageTitle: 'Date & Time Pickers'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            'resources/assets/global/plugins/clockface/css/clockface.css',
                            'resources/assets/global/plugins/bootstrap-datepicker/css/datepicker3.css',
                            'resources/assets/global/plugins/bootstrap-timepicker/css/bootstrap-timepicker.min.css',
                            'resources/assets/global/plugins/bootstrap-colorpicker/css/colorpicker.css',
                            'resources/assets/global/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css',
                            'resources/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css',

                            'resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'resources/assets/global/plugins/bootstrap-timepicker/js/bootstrap-timepicker.min.js',
                            'resources/assets/global/plugins/clockface/js/clockface.js',
                            'resources/assets/global/plugins/bootstrap-daterangepicker/moment.min.js',
                            'resources/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.js',
                            'resources/assets/global/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.js',
                            'resources/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js',

                            'resources/assets/admin/pages/scripts/components-pickers.js',

                            'resources/js/controllers/GeneralPageController.js'
                        ] 
                    }]);
                }] 
            }
        })

        // Custom Dropdowns
        .state('dropdowns', {
            url: "/dropdowns",
            templateUrl: "resources/views/dropdowns.html",
            data: {pageTitle: 'Custom Dropdowns'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load([{
                        name: 'MetronicApp',
                        files: [
                            'ources/assets/global/plugins/bootstrap-select/bootstrap-select.min.css',
                            'resources/assets/global/plugins/select2/select2.css',
                            'resources/assets/global/plugins/jquery-multi-select/css/multi-select.css',

                            'resources/assets/global/plugins/bootstrap-select/bootstrap-select.min.js',
                            'resources/assets/global/plugins/select2/select2.min.js',
                            'resources/assets/global/plugins/jquery-multi-select/js/jquery.multi-select.js',

                            'resources/assets/admin/pages/scripts/components-dropdowns.js',

                            'resources/js/controllers/GeneralPageController.js'
                        ] 
                    }]);
                }] 
            }
        }) 

        // Advanced Datatables
        .state('datatablesAdvanced', {
            url: "/datatables/advanced.html",
            templateUrl: "resources/views/datatables/advanced.html",
            data: {pageTitle: 'Advanced Datatables'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        files: [
                            'resources/assets/global/plugins/select2/select2.css',
                            'resources/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',
                            'resources/assets/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css',
                            'resources/assets/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css',

                            'resources/assets/global/plugins/select2/select2.min.js',
                            'resources/assets/global/plugins/datatables/all.min.js',
                            'resources/js/scripts/table-advanced.js',

                            'resources/js/controllers/GeneralPageController.js'
                        ]
                    });
                }]
            }
        })

        // Ajax Datetables
        .state('datatablesAjax', {
            url: "/datatables/ajax.html",
            templateUrl: "resources/views/datatables/ajax.html",
            data: {pageTitle: 'Ajax Datatables'},
            controller: "GeneralPageController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',
                        files: [
                            'resources/assets/global/plugins/select2/select2.css',                             
                            'resources/assets/global/plugins/bootstrap-datepicker/css/datepicker.css',
                            'resources/assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css',

                            'resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'resources/assets/global/plugins/select2/select2.min.js',
                            'resources/assets/global/plugins/datatables/all.min.js',

                            'resources/assets/global/scripts/datatable.js',
                            'js/scripts/table-ajax.js',

                            'resources/js/controllers/GeneralPageController.js'
                        ]
                    });
                }]
            }
        })

        // User Profile
        .state("profile", {
            url: "/profile",
            templateUrl: "resources/views/profile/main.html",
            data: {pageTitle: 'User Profile'},
            controller: "UserProfileController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({
                        name: 'MetronicApp',  
                        files: [
                            'resources/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css',
                            'resources/assets/admin/pages/css/profile.css',
                            'resources/assets/admin/pages/css/tasks.css',
                            
                            'resources/assets/global/plugins/jquery.sparkline.min.js',
                            'resources/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js',

                            'resources/assets/admin/pages/scripts/profile.js',

                            'resources/js/controllers/UserProfileController.js'
                        ]                    
                    });
                }]
            }
        })

        // User Profile Dashboard
        .state("profile.dashboard", {
            url: "/dashboard",
            templateUrl: "resources/views/profile/dashboard.html",
            data: {pageTitle: 'User Profile'}
        })

        // User Profile Account
        .state("profile.account", {
            url: "/account",
            templateUrl: "resources/views/profile/account.html",
            data: {pageTitle: 'User Account'}
        })

        // User Profile Help
        .state("profile.help", {
            url: "/help",
            templateUrl: "resources/views/profile/help.html",
            data: {pageTitle: 'User Help'}      
        })

        // Todo
        .state('todo', {
            url: "/todo",
            templateUrl: "resources/views/todo.html",
            data: {pageTitle: 'Todo'},
            controller: "TodoController",
            resolve: {
                deps: ['$ocLazyLoad', function($ocLazyLoad) {
                    return $ocLazyLoad.load({ 
                        name: 'MetronicApp',  
                        files: [
                            'resources/assets/global/plugins/bootstrap-datepicker/css/datepicker3.css',
                            'resources/assets/global/plugins/select2/select2.css',
                            'resources/assets/admin/pages/css/todo.css',
                            
                            'resources/assets/global/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js',
                            'resources/assets/global/plugins/select2/select2.min.js',

                            'resources/assets/admin/pages/scripts/todo.js',

                            'resources/js/controllers/TodoController.js'
                        ]                    
                    });
                }]
            }
        })

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
                            'resources/assets/admin/pages/scripts/login.js',
                            'resources/js/controllers/LoginController.js'
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