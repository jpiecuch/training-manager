<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js" data-ng-app="app"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js" data-ng-app="app"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" data-ng-app="app">
<!--<![endif]-->
<head>
    <title data-ng-bind="'gym-home.com | ' + $state.current.data.pageTitle"></title>

    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="Jakub Piecuch" name="author"/>

    <link href="//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css"/>
    <link href="resources/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

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
    <link href="resources/css/custom.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/toaster.css" rel="stylesheet" type="text/css"/>
    <link href="resources/css/angular-chart.css" rel="stylesheet" type="text/css"/>
    <link rel="shortcut icon" href="resources/favicon.ico"/>
</head>

<body ng-controller="AppController" ng-class="{'page-container-bg-solid': (settings.layout.pageBodySolid && settings.isUserSignIn), 'page-sidebar-closed': settings.layout.pageSidebarClosed && settings.isUserSignIn, 'login': !settings.isUserSignIn, 'page-header-fixed page-sidebar-closed-hide-logo page-quick-sidebar-over-content page-on-load' : settings.isUserSignIn}">

<div ng-spinner-bar class="page-spinner-bar">
    <div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div>
</div>

<div data-ng-if="settings.isUserSignIn" data-ng-include="'resources/tpl/header.html'" data-ng-controller="HeaderController"
     class="page-header navbar navbar-fixed-top">
</div>

<div class="clearfix"></div>

<div class="page-container">
    <div data-ng-if="settings.isUserSignIn" data-ng-include="'resources/tpl/sidebar.html'" data-ng-controller="SidebarController" class="page-sidebar-wrapper">
    </div>

    <div class="page-content-wrapper">
        <div ng-class="{'page-content' : settings.isUserSignIn}">
            <toaster-container toaster-options="{'time-out': 3000, 'close-button':true, 'positionClass': 'toast-top-right'}"></toaster-container>
            <div ui-view class="fade-in-up">
            </div>
        </div>
    </div>
</div>

<div data-ng-if="settings.isUserSignIn" data-ng-include="'resources/tpl/footer.html'" data-ng-controller="FooterController" class="page-footer">
</div>
<div data-ng-include="'resources/tpl/policy.html'" data-ng-controller="PolicyController"></div>
<div data-ng-include="'resources/tpl/terms.html'" data-ng-controller="TermsController"></div>
<script src="https://www.youtube.com/iframe_api"></script>
<!--[if lt IE 9]>
<script src="resources/assets/global/plugins/respond.min.js"></script>
<script src="resources/assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="resources/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/amcharts/amcharts.js" type="text/javascript"></script>
<script src="resources/js/modules/chart.js" type="text/javascript"></script>

<script src="resources/assets/global/plugins/angularjs/angular.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/angular-animate.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/angular-sanitize.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/angular-touch.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/angular-cookies.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/plugins/angular-ui-router.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/angularjs/plugins/ocLazyLoad.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate-loader-static-files.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate-loader-url.min.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-translate-storage-local.min.js" type="text/javascript"></script>
<script src="resources/js/modules/ui-bootstrap-tpls-0.12.0.min.js" type="text/javascript"></script>
<script src="resources/js/modules/textAngular.min.js" type="text/javascript"></script>
<script src="resources/js/modules/textAngular-sanitize.min.js" type="text/javascript"></script>
<script src="resources/js/modules/toaster.js" type="text/javascript"></script>
<script src="resources/js/modules/calendar.js" type="text/javascript"></script>
<script src="resources/js/modules/checklist-model.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-youtube-embed.js" type="text/javascript"></script>
<script src="resources/js/modules/tmhDynamicLocale.js" type="text/javascript"></script>
<script src="resources/js/modules/angular-chart.min.js" type="text/javascript"></script>

<script src="resources/js/app.js" type="text/javascript"></script>
<script src="resources/js/controllers/LocaleController.js" type="text/javascript"></script>
<script src="resources/js/controllers/PolicyController.js" type="text/javascript"></script>
<script src="resources/js/controllers/TermsController.js" type="text/javascript"></script>
<script src="resources/js/services/url-service.js" type="text/javascript"></script>
<script src="resources/js/services/input-service.js" type="text/javascript"></script>
<script src="resources/js/services/authenticate-service.js" type="text/javascript"></script>
<script src="resources/js/services/dictionary-service.js" type="text/javascript"></script>
<script src="resources/js/services/resource-service.js" type="text/javascript"></script>
<script src="resources/js/services/input-validate-service.js" type="text/javascript"></script>
<script src="resources/js/services/policy-service.js" type="text/javascript"></script>
<script src="resources/js/services/terms-service.js" type="text/javascript"></script>
<script src="resources/js/services/alert-service.js" type="text/javascript"></script>
<script src="resources/js/directives.js" type="text/javascript"></script>


<script src="resources/js/underscore-min.js" type="text/javascript"></script>
<script src="resources/js/underscore.string.min.js" type="text/javascript"></script>
<script src="resources/assets/global/plugins/moment.min.js" type="text/javascript"></script>
</body>

<script type="text/javascript">
    app.constant('contextPath', '${pageContext.request.contextPath}')
            .constant('lang', '${pageContext.response.locale.language}')
            .constant('version', 'v1')
            .constant('user', ${not empty user} === true ? { username: '${user.username}', authorities: '${user.authorities}', id: '${user.id}' } : null);
</script>
</html>