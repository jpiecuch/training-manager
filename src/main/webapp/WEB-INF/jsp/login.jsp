<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
    <!--<![endif]-->
    <head>
        <meta charset="utf-8"/>
        <title><spring:message code="app.title"/></title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
        <meta content="" name="description"/>
        <meta content="" name="author"/>
        <link href="//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/uniform/css/uniform.default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/select2/select2.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/plugins/select2/select2-metronic.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/style-metronic.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/style.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/style-responsive.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/plugins.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/themes/default.css"/>" rel="stylesheet" type="text/css" id="style_color"/>
        <link href="<c:url value="/resources/assets/css/pages/login.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <script src="<c:url value="/resources/assets/scripts/angularjs/angular.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/assets/scripts/angularjs/checklist-model.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/assets/scripts/angularjs/app.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/assets/scripts/angularjs/directives.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/assets/scripts/angularjs/filters.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/assets/scripts/angularjs/services.js"/>" type="text/javascript" ></script>
        
    </head>
    <body ng-app="training" class="login">
        <script type="text/javascript">
            training.controller("loginController", function($scope, $http) {
                $scope.init = function() {
                    $scope.activeView = 0;
                };
                
                $scope.reset = function(isValid) {
                    if (isValid) {
				alert('our form is amazing');
			} else {
                            alert('gówno');
                        }
                    $http.post('<c:url value="/auth/reset?email="/>' + $scope.email).success(function(data) {
                    });
                };
                
                $scope.create = function() {
                    $http.post('<c:url value="/auth/create"/>', $scope.user).success(function(data) {
                    });
                };
            });
        </script>
        <div class="logo"><a href="index.html"></a></div>
        <div ng-controller="loginController" ng-init="init()" class="content">
            <div ng-show="activeView === 0">
                <form class="login-form" method="POST" action="<c:url value="/j_spring_security_check"/>">
                <h3 class="form-title"><spring:message code="app.login.to.app"/></h3>
                <div class="alert alert-danger display-hide">
                    <button class="close" data-close="alert"></button>
                    <span><spring:message code="app.enter.username.and.password"/></span>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.name"/></label>
                    <div class="input-icon"><i class="fa fa-user"></i><input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="<spring:message code="user.name"/>" name="j_username"/></div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.password"/></label>
                    <div class="input-icon"><i class="fa fa-lock"></i><input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="<spring:message code="user.password"/>" name="j_password"/></div>
                </div>
                <div class="form-actions">
                    <label class="checkbox"><input id="j_remember" name="_spring_security_remember_me" type="checkbox" value="1" ng-uniform /> <spring:message code="user.remember.me"/> </label>
                    <button type="submit" class="btn green pull-right"><spring:message code="user.login"/> <i class="m-icon-swapright m-icon-white"></i></button>
                </div>
                <div class="forget-password">
                    <h4><spring:message code="user.forgot.password"/>?</h4>
                    <p><spring:message code="user.click"/> <a href="" ng-click="activeView = 1" id="forget-password"><spring:message code="user.here"/></a> <spring:message code="user.to.reset.password"/>.</p>
                </div>
                <div class="create-account"><p><spring:message code="user.no.account"/>?&nbsp;<a href="" ng-click="activeView = 2" id="register-btn"><spring:message code="user.create.account"/></a></p></div>
            </form>
            </div>
            <form name="resetForm" ng-submit="reset(resetForm.$valid)" ng-show="activeView === 1" novalidate>
                <h3><spring:message code="user.forgot.password"/> ?</h3>
                <p><spring:message code="user.enter.email.to.reset.password"/>.</p>
                <div class="form-group">
                    <div class="input-icon">
                        <i class="fa fa-envelope"></i>
                        <input required class="form-control placeholder-no-fix" type="email" name="email" placeholder="<spring:message code="user.email"/>" ng-model="email"/>
                    </div>
                    <p ng-show="resetForm.email.$invalid && !resetForm.email.$pristine" class="help-block"><spring:message code="user.email.required"/></p>
                </div>
                <div class="form-actions">
                    <button ng-click="activeView = 0" type="button" id="back-btn" class="btn"><i class="m-icon-swapleft"></i> <spring:message code="user.back"/> </button>
                    <button ng-disabled="resetForm.$invalid" type="submit" class="btn green pull-right"><spring:message code="user.send"/> <i class="m-icon-swapright m-icon-white"></i></button>
                </div>
            </form>
            <form ng-show="activeView === 2" class="register-form" ng-submit="create()">
                <h3><spring:message code="user.sign.up"/></h3>
                <p><spring:message code="user.enter.personal.data.below"/>:</p>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.firstName"/></label>
                    <div class="input-icon">
                        <i class="fa fa-font"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="<spring:message code="user.firstName"/>" ng-model="user.firstName"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.lastName"/></label>
                    <div class="input-icon">
                        <i class="fa fa-font"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="<spring:message code="user.lastName"/>" ng-model="user.lastName"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.email"/></label>
                    <div class="input-icon">
                        <i class="fa fa-envelope"></i>
                        <input class="form-control placeholder-no-fix" type="text" placeholder="<spring:message code="user.email"/>" ng-model="user.email"/>
                    </div>
                </div>
                <p>Enter your account details below:</p>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.name"/></label>
                    <div class="input-icon">
                        <i class="fa fa-user"></i>
                        <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="<spring:message code="user.name"/>" ng-model="user.name"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.password"/></label>
                    <div class="input-icon">
                        <i class="fa fa-lock"></i>
                        <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="<spring:message code="user.password"/>" ng-model="user.password"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.rPassword"/></label>
                    <div class="controls">
                        <div class="input-icon">
                            <i class="fa fa-check"></i>
                            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="<spring:message code="user.rPassword"/>" ng-model="rPassword"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label><input type="checkbox" name="tnc" ng-uniform/> <spring:message code="app.i.agree.to"/> <a href="#"> <spring:message code="app.terms.of.service"/></a> <spring:message code="app.and"/> <a href="#"> <spring:message code="app.privacy.policy"/> </a></label>
                    <div id="register_tnc_error"></div>
                </div>
                <div class="form-actions">
                    <button id="register-back-btn" ng-click="activeView = 0" type="button" class="btn"><i class="m-icon-swapleft"></i> <spring:message code="user.back"/> </button>
                    <button type="submit" id="register-submit-btn" class="btn green pull-right"> <spring:message code="user.sign.up"/> <i class="m-icon-swapright m-icon-white"></i></button>
                </div>
            </form>
        </div>
        <div class="copyright">
            2014 &copy; <spring:message code="app.title"/>.
        </div>
        <!--[if lt IE 9]>
            <script src="assets/plugins/respond.min.js"></script>
            <script src="assets/plugins/excanvas.min.js"></script> 
        <![endif]-->
        <script src="<c:url value="/resources/assets/plugins/jquery-1.10.2.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/jquery-migrate-1.2.1.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/jquery.blockui.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/select2/select2.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/assets/plugins/uniform/jquery.uniform.min.js"/>" type="text/javascript"></script>
    </body>
</html>