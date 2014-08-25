<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="page-header navbar navbar-fixed-top">
    <div class="page-header-inner">
        <div class="page-logo">
            <a href="<c:url value="/"/>">
                <img src="<c:url value="/resources/img/logo.png"/>" alt="logo" class="logo-default">
            </a>
            <div class="menu-toggler sidebar-toggler hide">
            </div>
        </div>
        <div class="top-menu">
            <ul class="nav navbar-nav pull-right">
                <li class="dropdown dropdown-user">
                    <a href="" class="dropdown-toggle">
                        <img alt="" class="img-circle" src="<c:url value="/resources/assets/admin/layout/img/avatar_small.png"/>"><span class="username"><sec:authentication property="principal.fullName"/></span><i class="fa fa-angle-down"></i>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/j_spring_security_logout" />"><i class="fa fa-key"></i> <spring:message code="logout"/></a></li>
                    </ul>
                </li>
            </ul>
            <div id="lang-icons" class="pull-right">
                <c:set var="queryString"><c:forEach items="${fn:split(fn:trim(pageContext.request.queryString), '&')}" var="q"><c:if test="${not fn:startsWith(q,'lang') and not empty q}"><c:out value="${q}&"/></c:if></c:forEach></c:set>
                <ul class="nav navbar-nav">
                    <li id="lang-pl">
                        <a class="${pageContext.response.locale.language eq 'pl' ? 'selected' : ''}" href="${requestScope['javax.servlet.forward.request_uri']}?${queryString}lang=pl"></a>
                    </li>
                    <li id="lang-en">
                        <a class="${pageContext.response.locale.language eq 'en' ? 'selected' : ''}" href="${requestScope['javax.servlet.forward.request_uri']}?${queryString}lang=en"></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>