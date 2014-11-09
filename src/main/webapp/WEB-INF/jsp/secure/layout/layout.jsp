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
    <body ng-app="training" class="page-header-fixed page-quick-sidebar-over-content" ng-controller="SidebarCtrl" ng-class="{'page-sidebar-closed': menu.isClosed}" >
        <tiles:insertAttribute name="header"/>
        <div class="clearfix"></div>
        <div class="page-container">
            <div growl></div>
            <c:set var="fullContextPath" value="${pageContext.request.contextPath}/"/>
            <div class="page-sidebar-wrapper" >
            <div class="page-sidebar navbar-collapse collapse">
            <ul class="page-sidebar-menu" ng-class="{'page-sidebar-menu-closed': menu.isClosed}">
            <li class="sidebar-toggler-wrapper">
                <div class="sidebar-toggler" ng-click="menu.state()"></div>
            </li></br>
            <li ng-repeat="e in menu.elements" class="start" ng-class="{active: menu.isActive(e.url)}">
                <a href="{{e.url}}">
                    <i class="{{e.icon}}"></i>
                    <span class="title">{{e.code}}</span>
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
            <div class="page-footer-inner">2014 &copy; <a href="<c:url value="/"/> "><spring:message code="app.title"/></a> | <span><a href="" ng-click="link('policy')"><spring:message code="privacy.policy"/></a></span> | <span><a href="" ng-click="link('terms')"><spring:message code="terms.of.service"/></a></span></div>
            <div class="page-footer-tools"><span class="go-top"><i class="fa fa-angle-up"></i></span></div>
        </div>
        <%@include file="../policy.jsp" %>
        <tiles:insertAttribute name="end"/>
        <script type="text/javascript">
            training.constant('contextPath', '${pageContext.request.contextPath}')
                    .constant('lang', '${pageContext.response.locale.language}')
                    .constant('version', 'v1');
        </script>
    </body>
</html>