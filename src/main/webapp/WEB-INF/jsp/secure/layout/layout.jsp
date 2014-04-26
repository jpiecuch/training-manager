<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
    <tiles:insertAttribute name="start" />
    <body ng-app="training" class="page-header-fixed">
        <tiles:insertAttribute name="header"/>
        <div class="clearfix"></div>
        <div class="page-container">
            <div class="page-sidebar-wrapper">
                <div class="page-sidebar navbar-collapse collapse">
                    <ul class="page-sidebar-menu">
                        <li class="sidebar-toggler-wrapper"><div class="sidebar-toggler hidden-phone"></div></li>
                        <li class="start"><a href="<c:url value="/"/>"><i class="fa fa-home"></i><span class="title">Dashboard</span></a></li>
                        <li class="active"><a href="<c:url value="/plan/calendar.html"/>"><i class="fa fa-calendar"></i><span class="title">Calendar</span></a></li> 
                    </ul>
                </div>
            </div>
            <div class="page-content-wrapper">
                <div class="page-content">
                    <div class="row"><div class="col-md-12"><tiles:insertAttribute name="body"/></div></div>
                </div>
            </div>
        </div>
        <div class="footer">
            <div class="footer-inner">2014 &copy; <spring:message code="app.title"/></div>
            <div class="footer-tools"><span class="go-top"><i class="fa fa-angle-up"></i></span></div>
        </div>
        <tiles:insertAttribute name="end"/>
    </body>
</html>
