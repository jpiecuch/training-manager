<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header navbar navbar-fixed-top">
    <div class="header-inner">
        <a class="navbar-brand" href="<c:url value="/"/>"><img src="<c:url value="/resources/assets/img/logo.png"/>" alt="logo" class="img-responsive"/></a>
        <ul class="nav navbar-nav pull-right">
            <li class="dropdown" id="header_notification_bar">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                    <i class="fa fa-warning"></i><span class="badge">6</span>
                </a>
                <ul class="dropdown-menu extended notification">
                    <li><p>You have 14 new notifications</p></li>
                    <li><ul class="dropdown-menu-list scroller" style="height: 250px;"></ul></li>
                    <li class="external"><a href="#">See all notifications <i class="m-icon-swapright"></i></a></li>
                </ul>
            </li>
            <li class="dropdown" id="header_inbox_bar">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                    <i class="fa fa-envelope"></i><span class="badge">5</span>
                </a>
                <ul class="dropdown-menu extended inbox">
                    <li><p>You have 12 new messages</p></li>
                    <li><ul class="dropdown-menu-list scroller" style="height: 250px;">  </ul></li>
                    <li class="external"><a href="inbox.html">See all messages <i class="m-icon-swapright"></i></a></li>
                </ul>
            </li>
            <li class="dropdown" id="header_task_bar">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                    <i class="fa fa-tasks"></i><span class="badge">5</span>
                </a>
                <ul class="dropdown-menu extended tasks">
                    <li><p>You have 12 pending tasks</p></li>
                    <li><ul class="dropdown-menu-list scroller" style="height: 250px;"></ul></li>
                    <li class="external"><a href="#">See all tasks <i class="m-icon-swapright"></i></a></li>
                </ul>
            </li>
            <li class="dropdown user">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                    <span class="username"><sec:authentication property="principal.fullName"/></span><i class="fa fa-angle-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="extra_profile.html"><i class="fa fa-user"></i> My Profile</a></li>
                    <li><a href="page_calendar.html"><i class="fa fa-calendar"></i> My Calendar</a></li>
                    <li><a href="inbox.html"><i class="fa fa-envelope"></i> My Inbox<span class="badge badge-danger">3</span></a></li>
                    <li><a href="#"><i class="fa fa-tasks"></i> My Tasks<span class="badge badge-success">7</span></a></li>
                    <li class="divider"></li>
                    <li><a href="javascript:;" id="trigger_fullscreen"><i class="fa fa-arrows"></i> Full Screen</a></li>
                    <li><a href="extra_lock.html"><i class="fa fa-lock"></i> Lock Screen</a></li>
                    <li><a href="<c:url value="/j_spring_security_logout" />"><i class="fa fa-key"></i> Log Out</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>