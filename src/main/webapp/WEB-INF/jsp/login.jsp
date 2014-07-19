<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

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
        <link href="<c:url value="/resources/assets/global/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/global/plugins/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/admin/layout/css/layout.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/admin/layout/css/themes/default.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/global/css/plugins.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/admin/pages/css/login-soft.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value="/resources/assets/global/css/plugins.css"/>" rel="stylesheet" type="text/css">
        <link href="<c:url value="/resources/assets/global/css/components.css"/>" rel="stylesheet" type="text/css">
        <link rel="icon" href="<c:url value="/resources/favicon.ico"/>" sizes="16x16 32x32 48x48 64x64" type="image/vnd.microsoft.icon">
    </head>
    <body ng-app="training" ng-controller="loginController" ng-init="init()" class="login" backstretch bgs="{{bgs}}">
        <div class="logo">
            <img src="<c:url value="resources/img/logo-big.png"/>" alt="">
        </div>
        <div class="content" >
            <div ng-show="activeView === 0">
                    <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION']}">
                        <c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
                        <div class="alert alert-danger alert-dismissable"> <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><spring:message code="user.login.incorrect"/></div>
                    </c:if>
                    <c:if test="${not empty sessionScope['user.activated']}">
                        <c:choose>
                            <c:when test="${sessionScope['user.activated']}">
                                <div class="alert alert-success alert-dismissable"> <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><spring:message code="user.activated"/></div>
                            </c:when>
                            <c:when test="${not sessionScope['user.activated']}">
                                <div class="alert alert-warning alert-dismissable"> <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button><spring:message code="user.not.activated"/></div>
                            </c:when>
                        </c:choose>
                        <c:remove var="user.activated" scope = "session" />
                    </c:if>
                    <form class="login-form" name="loginForm" method="POST" action="<c:url value="/j_spring_security_check"/>" novalidate>
                        <h3 class="form-title"><spring:message code="app.login.to.app"/></h3>
                        <div class="form-group" ng-class="inputStatus(loginForm.j_username)">
                            <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.name"/></label>
                            <div class="input-icon"><i class="fa fa-user"></i><input required class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="<spring:message code="user.name"/>" name="j_username" ng-model="loginUser.j_username"/></div>
                            <p ng-show="inputValid(loginForm.j_username)" class="help-block">
                                <span ng-show="loginForm.j_username.$error.required"><spring:message code="user.name.required.error"/></span>
                            </p>
                        </div>
                        <div class="form-group" ng-class="inputStatus(loginForm.j_password)">
                            <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.password"/></label>
                            <div class="input-icon"><i class="fa fa-lock"></i><input required class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="<spring:message code="user.password"/>" name="j_password" ng-model="loginUser.j_password"/></div>
                            <p ng-show="inputValid(loginForm.j_password)" class="help-block">
                                <span ng-show="loginForm.j_password.$error.required"><spring:message code="user.password.required.error"/></span>
                            </p>
                        </div>
                        <div class="form-actions">
                            <label class="checkbox"><input id="j_remember" name="_spring_security_remember_me" type="checkbox" value="1" /><label for="j_remember"><span></span></label> <spring:message code="user.remember.me"/> </label>
                            <button ng-disabled="loginForm.$invalid" type="submit" class="btn green pull-right"><spring:message code="user.login"/> <i class="m-icon-swapright m-icon-white"></i></button>
                        </div>
                    </form>
                        <div class="login-options">
			<h4><spring:message code="user.login.with"/> </h4>
			<ul class="social-icons">
                            <li ng-repeat="s in socials">
                                <a href="" ng-click="socialSubmit(s)" class="{{s.id}}" data-original-title="{{s.id}}" ></a>
                                
                                <form id="{{s.id}}-form" action="{{socialURL + '/' + s.id}}" method="post">
                                    <input ng-if="s.scope" type="hidden" name="scope" value="{{s.scope}}">
                                </form>
                            </li>
			</ul>
                        </div>
                        <div class="forget-password">
                            <h4><spring:message code="user.forgot.password"/></h4>
                            <p><spring:message code="user.click"/> <a href="" ng-click="activeView = 1" id="forget-password-btn"><spring:message code="user.here"/></a> <spring:message code="user.to.reset.password"/></p>
                        </div>
                        <div class="create-account"><p><spring:message code="user.no.account"/>&nbsp;<a href="" ng-click="activeView = 2" id="register-btn"><spring:message code="user.create.account"/></a></p></div>
            </div>
            <form name="resetForm" ng-submit="reset()" ng-show="activeView === 1" novalidate>
                <h3><spring:message code="user.forgot.password"/> </h3>
                <p><spring:message code="user.enter.email.to.reset.password"/></p>
                <div class="form-group" ng-class="inputStatus(resetForm.email)">
                    <div class="input-icon">
                        <i class="fa fa-envelope"></i>
                        <input required class="form-control placeholder-no-fix" type="email" name="email" placeholder="<spring:message code="user.email"/>" ng-model="email"/>
                    </div>
                    <p ng-show="inputValid(resetForm.email)" class="help-block">
                        <span ng-show="resetForm.email.$error.email"><spring:message code="user.email.format.error"/></span>
                        <span ng-show="resetForm.email.$error.required"><spring:message code="user.email.required.error"/></span>
                    </p>
                </div>
                <div class="form-actions">
                    <button ng-click="activeView = 0" type="button" id="back-btn" class="btn"><i class="m-icon-swapleft"></i> <spring:message code="back"/> </button>
                    <button ng-disabled="resetForm.$invalid" type="submit" class="btn green pull-right"><spring:message code="send"/> <i class="m-icon-swapright m-icon-white"></i></button>
                </div>
            </form>
                <form ng-show="activeView === 2" name="createForm" ng-submit="create()" novalidate>
                <h3><spring:message code="user.sign.up"/></h3>
                <p><spring:message code="user.enter.personal.data.below"/></p>
                <div class="form-group" ng-class="inputStatus(createForm.firstName)">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.firstName"/></label>
                    <div class="input-icon">
                        <i class="fa fa-font"></i>
                        <input name="firstName" required class="form-control placeholder-no-fix" type="text" placeholder="<spring:message code="user.firstName"/>" ng-model="user.firstName"/>
                    </div>
                    <span ng-show="inputValid(createForm.firstName)" class="help-block"><spring:message code="user.firstName.required.error"/></span>
                </div>
                <div class="form-group" ng-class="inputStatus(createForm.lastName)">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.lastName"/></label>
                    <div class="input-icon">
                        <i class="fa fa-font"></i>
                        <input name="lastName" required class="form-control placeholder-no-fix" type="text" placeholder="<spring:message code="user.lastName"/>" ng-model="user.lastName"/>
                    </div>
                    <span ng-show="inputValid(createForm.lastName)" class="help-block"><spring:message code="user.lastName.required.error"/></span>
                </div>
                <div class="form-group" ng-class="inputStatus(createForm.email)">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.email"/></label>
                    <div class="input-icon">
                        <i class="fa fa-envelope"></i>
                        <input ng-availability="email" ng-pattern="/<spring:eval expression="@propertyConfigurer.getProperty('user.email.pattern')" />/" name="email" required class="form-control placeholder-no-fix" type="email" placeholder="<spring:message code="user.email"/>" ng-model="user.email"/>
                    </div>
                    <p ng-show="inputValid(createForm.email)">
                        <span ng-show="createForm.email.$error.required" class="help-block"><spring:message code="user.email.required.error"/><br/></span>
                        <span ng-show="createForm.email.$error.pattern" class="help-block"><spring:message code="user.email.format.error"/><br/></span>
                        <span ng-show="createForm.email.$error.availability" class="help-block"><spring:message code="user.email.availability.error"/></span>
                    </p>
                </div>
                <p><spring:message code="user.email.account.info"/></p>
                <div class="form-group" ng-class="inputStatus(createForm.name)">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.name"/></label>
                    <div class="input-icon">
                        <i class="fa fa-user"></i>
                        <spring:eval var="nameMaxLength" expression="@propertyConfigurer.getProperty('user.name.maxLength')" />
                        <spring:eval var="nameMinLength" expression="@propertyConfigurer.getProperty('user.name.minLength')" />
                        <input ng-availability="name" ng-maxlength="${nameMaxLength}" ng-minlength="${nameMinLength}" ng-pattern="/<spring:eval expression="@propertyConfigurer.getProperty('user.name.pattern')" />/" name="name" required class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="<spring:message code="user.name"/>" ng-model="user.name"/>
                    </div>
                    <p ng-show="inputValid(createForm.name)">
                        <span ng-show="createForm.name.$error.required" class="help-block"><spring:message code="user.name.required.error"/></span>
                        <span ng-show="createForm.name.$error.pattern" class="help-block"><spring:message code="user.name.pattern.error"/></span>
                        <span ng-show="createForm.name.$error.minlength" class="help-block"><spring:message code="user.name.minLength.error" arguments="${nameMinLength}"/></span>
                        <span ng-show="createForm.name.$error.maxlength" class="help-block"><spring:message code="user.name.maxLength.error" arguments="${nameMaxLength}"/></span>
                        <span ng-show="createForm.name.$error.availability" class="help-block"><spring:message code="user.name.availability.error"/></span>
                    </p>
                </div>
                <div class="form-group" ng-class="inputStatus(createForm.password)">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.password"/></label>
                    <div class="input-icon">
                        <i class="fa fa-lock"></i>
                        <spring:eval var="passwordMaxLength" expression="@propertyConfigurer.getProperty('user.password.maxLength')" />
                        <spring:eval var="passwordMinLength" expression="@propertyConfigurer.getProperty('user.password.minLength')" />
                        <input ng-maxlength="${passwordMaxLength}" ng-minlength="${passwordMinLength}" ng-pattern="/<spring:eval expression="@propertyConfigurer.getProperty('user.password.pattern')" />/" name="password" required class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password" placeholder="<spring:message code="user.password"/>" ng-model="user.password"/>
                    </div>
                    <p ng-show="inputValid(createForm.password)">
                        <span ng-show="createForm.password.$error.required" class="help-block"><spring:message code="user.password.required.error"/></span>
                        <span ng-show="createForm.password.$error.pattern" class="help-block"><spring:message code="user.password.pattern.error"/></span>
                        <span ng-show="createForm.password.$error.minlength" class="help-block"><spring:message code="user.password.minLength.error" arguments="${passwordMinLength}"/></span>
                        <span ng-show="createForm.password.$error.maxlength" class="help-block"><spring:message code="user.password.maxLength.error" arguments="${passwordMaxLength}"/></span>
                    </p>
                </div>
                <div class="form-group" ng-class="inputStatus(createForm.rPassword)">
                    <label class="control-label visible-ie8 visible-ie9"><spring:message code="user.rPassword"/></label>
                    <div class="controls">
                        <div class="input-icon">
                            <i class="fa fa-check"></i>
                            <input name="rPassword" required class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="<spring:message code="user.rPassword"/>" ng-model="user.rPassword" repeat-password="password"/>
                        </div>
                        <p ng-show="createForm.rPassword.$invalid && !createForm.rPassword.$pristine">
                            <span ng-show="createForm.rPassword.$error.repeat" class="help-block"><spring:message code="user.password.unique.error"/></span>
                            <span ng-show="createForm.rPassword.$error.required" class="help-block"><spring:message code="user.rPassword.required.error"/></span>
                        </p>
                    </div>
                </div>
                <div class="form-group" ng-class="inputStatus(createForm.tnc)">
                    <label><input required type="checkbox" ng-model="accept" name="tnc" id="tnc" /><label for="tnc"><span></span></label> <spring:message code="app.i.agree.to"/> <a href="#"> <spring:message code="app.terms.of.service"/></a> <spring:message code="app.and"/> <a href="#"> <spring:message code="app.privacy.policy"/> </a></label>
                    <span ng-show="createForm.tnc.$invalid && !createForm.tnc.$pristine" class="help-block"><spring:message code="user.tnc.required.error"/></span>
                </div>
                <div class="form-actions">
                    <button id="register-back-btn" ng-click="activeView = 0" type="button" class="btn"><i class="m-icon-swapleft"></i> <spring:message code="back"/> </button>
                    <button ng-disabled="createForm.$invalid" type="submit" id="register-submit-btn" class="btn green pull-right"> <spring:message code="signUp"/> <i class="m-icon-swapright m-icon-white"></i></button>
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
        <script src="<c:url value="/resources/assets/global/plugins/jquery-1.11.0.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/plugins/jquery-backstretch/jquery.backstretch.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/js/angularjs/angular.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-cookies.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-sanitize.min.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-backstretch.js"/>" type="text/javascript" ></script>
        
        <script src="<c:url value="/resources/js/angularjs/modules/angular-translate.min.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-translate.min.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-translate-loader-url.min.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-translate-loader-static-files.min.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-translate-storage-local.min.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-translate-storage-cookie.min.js"/>" type="text/javascript" ></script>
        
        <script src="<c:url value="/resources/js/angularjs/modules/ui-bootstrap-tpls-0.10.0.min.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/calendar.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/perfect-scrollbar.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-dragdrop.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-growl.js"/>" type="text/javascript" ></script>
        
        <script src="<c:url value="/resources/js/angularjs/modules/app.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/checklist-model.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/directives.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/filters.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/services.js"/>" type="text/javascript" ></script>  
        <script src="<c:url value="/resources/assets/global/plugins/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
        <script src="<c:url value="/resources/js/angularjs/modules/ng-table.min.js"/>" type="text/javascript" ></script>
        <script src="<c:url value="/resources/js/angularjs/modules/angular-youtube-embed.min.js"/>" type="text/javascript" ></script>
        <script type="text/javascript">
            training.constant('contextPath', '${pageContext.request.contextPath}');
            training.controller("loginController", function($scope, $http) {
                $scope.init = function() {
                    $scope.activeView = 0;
                    $http.get('<c:url value="/resource/names/image/start-bg"/>').success(function(data) {
                        var bgs = [];
                        for(var i = 0; i < data.length; i++) {
                            bgs.push('<c:url value="/resource/content/start-bg/"/>' + data[i]);
                        }
                        $scope.bgs = bgs.join();
                    });
                    
                    $http.get('<c:url value="/api/dictionary/social"/>').success(function(data) {
                        $scope.socials = data;
                        $scope.socialURL = '<c:url value="/signin"/>';
                    });               
                };
                
                $scope.reset = function() {
                    $http.post('<c:url value="/authentication/reset?email="/>' + $scope.email).success(function(data) {});
                };
                
                $scope.create = function() {
                    $http.post('<c:url value="/authentication/create"/>', $scope.user).success(function(data) {
                    });
                };
                
                $scope.inputStatus = function(input) {
                    return $scope.inputValid(input) ? 'has-error' : input.$valid && !input.$pristine ? 'has-success' : '';
                };
                
                $scope.inputValid = function(input) {
                    return input.$invalid && !input.$pristine;
                };
                
                $scope.socialSignIn = function(social) {
                    $http.post('<c:url value="/signin/"/>' + social);
                };
                
                $scope.socialSubmit = function(s) {
                    document.getElementById(s.id + '-form').submit();
                };
            });
        </script>
    </body>
</html>