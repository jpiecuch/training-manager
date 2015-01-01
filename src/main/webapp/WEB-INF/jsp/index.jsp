<!DOCTYPE html>
<!--
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.2.0
Version: 3.4
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js" data-ng-app="MetronicApp"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js" data-ng-app="MetronicApp"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" data-ng-app="MetronicApp">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <title data-ng-bind="'Metronic AngularJS | ' + $state.current.data.pageTitle"></title>

    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>

    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->

    <!-- BEGIN DYMANICLY LOADED CSS FILES(all plugin and page related styles must be loaded between GLOBAL and THEME css files ) -->
    <link id="ng_load_plugins_before"/>
    <!-- END DYMANICLY LOADED CSS FILES -->

    <!-- BEGIN THEME STYLES -->
    <!-- DOC: To use 'rounded corners' style just load 'components-rounded.css' stylesheet instead of 'components.css' in the below style tag -->
    <link href="resources/assets/global/css/components.css" id="style_components" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/admin/layout/css/layout.css" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/admin/layout/css/themes/darkblue.css" rel="stylesheet" type="text/css"
          id="style_color"/>
    <link href="resources/assets/admin/layout/css/custom.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME STYLES -->

    <link rel="shortcut icon" href="favicon.ico"/>
</head>
<!-- END HEAD -->

<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<body ng-controller="AppController"

      ng-class="{'page-container-bg-solid': (settings.layout.pageBodySolid && settings.isUserSignIn), 'page-sidebar-closed': settings.layout.pageSidebarClosed && settings.isUserSignIn, 'login': !settings.isUserSignIn, 'page-header-fixed page-sidebar-closed-hide-logo page-quick-sidebar-over-content page-on-load' : settings.isUserSignIn}">

<!-- BEGIN PAGE SPINNER -->
<div ng-spinner-bar class="page-spinner-bar">
    <div class="bounce1"></div>
    <div class="bounce2"></div>
    <div class="bounce3"></div>
</div>
<!-- END PAGE SPINNER -->

<!-- BEGIN HEADER -->
<div data-ng-if="settings.isUserSignIn" data-ng-include="'resources/tpl/header.html'" data-ng-controller="HeaderController"
     class="page-header navbar navbar-fixed-top">
</div>
<!-- END HEADER -->

<div class="clearfix">
</div>

<!-- BEGIN CONTAINER -->
<div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <div data-ng-if="settings.isUserSignIn" data-ng-include="'resources/tpl/sidebar.html'" data-ng-controller="SidebarController" class="page-sidebar-wrapper">
    </div>
    <!-- END SIDEBAR -->

    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <div ng-class="{'page-content' : settings.isUserSignIn}">
            <!-- BEGIN ACTUAL CONTENT -->
            <div ui-view class="fade-in-up">
            </div>
            <!-- END ACTUAL CONTENT -->
        </div>
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<!-- BEGIN FOOTER -->
<div data-ng-if="settings.isUserSignIn" data-ng-include="'resources/tpl/footer.html'" data-ng-controller="FooterController" class="page-footer">
</div>
<!-- END FOOTER -->

<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

<!-- BEGIN CORE JQUERY PLUGINS -->
<!--[if lt IE 9]>
<script src="resources/assets/global/plugins/respond.min.js"></script>
<script src="resources/assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="resources/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<!-- END CORE JQUERY PLUGINS -->

<!-- BEGIN CORE ANGULARJS PLUGINS -->
<script src="resources/assets/global/plugins/angularjs/angular.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/angular-sanitize.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/angular-touch.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/angular-cookies.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/plugins/angular-ui-router.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/plugins/ocLazyLoad.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/plugins/ui-bootstrap-tpls.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate-loader-static-files.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate-loader-url.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate-storage-cookie.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate-storage-local.min.js" type="text/javascript"></script>
<!-- END CORE ANGULARJS PLUGINS -->

<!-- BEGIN APP LEVEL ANGULARJS SCRIPTS -->
<script src="resources/js/app.js" type="text/javascript"></script>
<script src="resources/js/services/url-service.js" type="text/javascript"></script>
<script src="resources/js/services/authenticate-service.js" type="text/javascript"></script>
<script src="resources/js/services/resource-service.js" type="text/javascript"></script>
<script src="resources/js/services/input-validate-service.js" type="text/javascript"></script>
<script src="resources/js/services/policy-service.js" type="text/javascript"></script>
<script src="resources/js/directives.js" type="text/javascript"></script>
<!-- END APP LEVEL ANGULARJS SCRIPTS -->

<script src="resources/js/underscore-min.js" type="text/javascript"></script>
<script src="resources/js/underscore.string.min.js" type="text/javascript"></script>
</body>
<!-- END BODY -->
<script type="text/javascript">
    MetronicApp.constant('contextPath', '${pageContext.request.contextPath}')
            .constant('lang', '${pageContext.response.locale.language}')
            .constant('version', 'v1')
            .constant('isSignIn', ${isSignIn});
</script>
</html>