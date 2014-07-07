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
    <body ng-app="training" class="page-header-fixed page-quick-sidebar-over-content page-full-width">
        <script type="text/javascript">training.constant('contextPath', '${pageContext.request.contextPath}');</script>
        <tiles:insertAttribute name="header"/>
        <div class="clearfix"></div>
        <div class="page-container">
            <div growl></div>
            <div class="page-content-wrapper">
                <div class="page-content">
                    <div class="row"><div class="col-md-12"><tiles:insertAttribute name="body"/></div></div>
                </div>
            </div>
        </div>
        <div class="page-footer">
            <div class="page-footer-inner">2014 &copy; <spring:message code="app.title"/></div>
            <div class="page-footer-tools"><span class="go-top"><i class="fa fa-angle-up"></i></span></div>
        </div>
        <tiles:insertAttribute name="end"/>
    </body>
</html>