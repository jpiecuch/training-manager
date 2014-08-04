<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
    <tiles:insertAttribute name="start" />
    <body ng-app="training" ng-controller="sidebarController" class="page-header-fixed page-quick-sidebar-over-content" ng-class="hide ? 'page-sidebar-closed' : ''">
        <script type="text/javascript">training.constant('contextPath', '${pageContext.request.contextPath}');</script>
        <tiles:insertAttribute name="header"/>
        <div class="clearfix"></div>
        <div class="page-container">
            <div growl></div>
            <c:set var="fullContextPath" value="${pageContext.request.contextPath}/"/>
            <div class="page-sidebar-wrapper" >
            <script type="text/javascript">
                training.controller('sidebarController', function($scope) {
                    $scope.hide = true;

                    $scope.sidebar = function() {
                        $scope.hide = !$scope.hide;
                    }
                });
            </script>
            <div class="page-sidebar navbar-collapse collapse">
            <ul class="page-sidebar-menu" ng-class="hide ? 'page-sidebar-menu-closed' : ''">
            <li class="sidebar-toggler-wrapper">
                <div class="sidebar-toggler" ng-click="sidebar()"></div>
            </li></br>
            <li class="start ${fn:endsWith(requestScope['javax.servlet.forward.request_uri'], '/index.html') or requestScope['javax.servlet.forward.request_uri'] eq fullContextPath  ? 'active' : ''}">
                <a href="<c:url value='/'/>">
                    <i class="icon-home"></i>
                    <span class="title"><spring:message code="dashboard"/></span>
                    <span class="selected"></span>
                </a>
            </li>
            <li class="start ${fn:endsWith(requestScope['javax.servlet.forward.request_uri'], '/plan/calendar.html') ? 'active' : ''}">
                <a href="<c:url value='/plan/calendar.html'/>">
                    <i class="icon-calendar"></i>
                    <span class="title"><spring:message code="calendar.calendar"/></span>
                    <span class="selected"></span>
                </a>
            </li>
                <li class="start ${fn:endsWith(requestScope['javax.servlet.forward.request_uri'], '/dictionary/exercises.html') ? 'active' : ''}">
                    <a href="<c:url value='/dictionary/exercises.html'/>">
                        <i class="icon-doc"></i>
                        <span class="title"><spring:message code="exercises"/></span>
                        <span class="selected"></span>
                    </a>
                </li>
            </ul>
            </div>
            </div>
            <div class="page-content-wrapper">
                <div class="page-content">
                    <div class="row"><div class="col-md-12"><tiles:insertAttribute name="body"/></div></div>
                </div>
            </div>
        </div>
        <div class="page-footer" ng-controller="footerController">
            <script type="text/javascript">
                training.controller('footerController', function($scope, $http, $rootScope) {
                    $scope.link = function(type) {
                        $rootScope.$broadcast(type === 'policy' ? 'showPolicyEvent' : 'showTermsEvent');
                    }
                });
            </script>
            <div class="page-footer-inner">2014 &copy; <a href="<c:url value="/"/> "><spring:message code="app.title"/></a> | <span><a href="" ng-click="link('policy')"><spring:message code="privacy.policy"/></a></span> | <span><a href="" ng-click="link('terms')"><spring:message code="terms.of.service"/></a></span></div>
            <div class="page-footer-tools"><span class="go-top"><i class="fa fa-angle-up"></i></span></div>
        </div>
        <%@include file="../policy.jsp" %>
        <tiles:insertAttribute name="end"/>
    </body>
</html>