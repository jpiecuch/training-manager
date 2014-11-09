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
                <li data-ng-controller="QuickSidebarCtrl" class="dropdown dropdown-quick-sidebar-toggler">
                    <a href="" data-ng-click="quickSidebar.toggle()" class="dropdown-toggle">
                        <i data-ng-class="{'icon-logout' : quickSidebar.isVisible, 'icon-login' : !quickSidebar.isVisible}"></i>
                    </a>
                    <div class="page-quick-sidebar-wrapper" data-ng-class="{'page-quick-sidebar-open' : quickSidebar.isVisible}">
                        <div class="page-quick-sidebar">
                            <div class="nav-justified">
                                <ul class="nav nav-tabs nav-justified">
                                    <li data-ng-class="{'active' : quickSidebar.tab.isActive('information')}"><a href="" data-ng-click="quickSidebar.tab.select('information')" data-ng-bind="'personal.information' | translate"></a></li>
                                    <li data-ng-class="{'active' : quickSidebar.tab.isActive('alerts')}"><a href="" data-ng-click="quickSidebar.tab.select('alerts')" data-ng-bind="'personal.alerts' | translate"></a></li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active page-quick-sidebar-chat" data-ng-if="quickSidebar.tab.isActive('information')">
                                        <div class="page-quick-sidebar-chat-users">
                                            <h3 class="list-heading">Staff</h3>
                                            <ul class="media-list list-items">
                                                <li class="media">
                                                    <div class="media-status"><span class="badge badge-success">8</span></div>
                                                    <img class="media-object" src="../../assets/admin/layout/img/avatar3.jpg" alt="...">
                                                    <div class="media-body">
                                                        <h4 class="media-heading">Bob Nilson</h4>
                                                        <div class="media-heading-sub">
                                                            Project Manager
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>
                                            <h3 class="list-heading">Customers</h3>
                                            <ul class="media-list list-items">
                                                <li class="media">
                                                    <div class="media-status">
                                                        <span class="badge badge-warning">2</span>
                                                    </div>
                                                    <img class="media-object" src="../../assets/admin/layout/img/avatar6.jpg" alt="...">
                                                    <div class="media-body">
                                                        <h4 class="media-heading">Lara Kunis</h4>
                                                        <div class="media-heading-sub">
                                                            CEO, Loop Inc
                                                        </div>
                                                        <div class="media-heading-small">
                                                            Last seen 03:10 AM
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="page-quick-sidebar-item">
                                            <div class="page-quick-sidebar-chat-user">
                                                <div class="page-quick-sidebar-nav">
                                                    <a href="javascript:;" class="page-quick-sidebar-back-to-list"><i class="icon-arrow-left"></i>Back</a>
                                                </div>
                                                <div class="page-quick-sidebar-chat-user-messages">
                                                    <div class="post out">
                                                        <img class="avatar" alt="" src="../../assets/admin/layout/img/avatar3.jpg"/>
                                                        <div class="message">
                                                            <span class="arrow"></span>
                                                            <a href="#" class="name">Bob Nilson</a>
                                                            <span class="datetime">20:15</span>
                                                                <span class="body">
                                                                When could you send me the report ? </span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="page-quick-sidebar-chat-user-form">
                                                    <div class="input-group">
                                                        <input type="text" class="form-control" placeholder="Type a message here...">
                                                        <div class="input-group-btn">
                                                            <button type="button" class="btn blue"><i class="icon-paper-clip"></i></button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane active page-quick-sidebar-alerts" data-ng-if="quickSidebar.tab.isActive('alerts')">
                                        <div class="page-quick-sidebar-alerts-list">
                                            <h3 class="list-heading">General</h3>
                                            <ul class="feeds list-items">
                                                <li>
                                                    <div class="col1">
                                                        <div class="cont">
                                                            <div class="cont-col1">
                                                                <div class="label label-sm label-info">
                                                                    <i class="fa fa-check"></i>
                                                                </div>
                                                            </div>
                                                            <div class="cont-col2">
                                                                <div class="desc">
                                                                    You have 4 pending tasks. <span class="label label-sm label-warning ">
                                                                        Take action <i class="fa fa-share"></i>
                                                                        </span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col2">
                                                        <div class="date">
                                                            Just now
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>
                                            <h3 class="list-heading">System</h3>
                                            <ul class="feeds list-items">
                                                <li>
                                                    <div class="col1">
                                                        <div class="cont">
                                                            <div class="cont-col1">
                                                                <div class="label label-sm label-info">
                                                                    <i class="fa fa-check"></i>
                                                                </div>
                                                            </div>
                                                            <div class="cont-col2">
                                                                <div class="desc">
                                                                    You have 4 pending tasks. <span class="label label-sm label-warning ">
                                                                    Take action <i class="fa fa-share"></i>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col2">
                                                        <div class="date">
                                                            Just now
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
            </ul>
            <div id="lang-icons" class="pull-right" data-ng-controller="LocaleCtrl" data-ng-init="locale.init()">
                <ul class="nav navbar-nav">
                    <li data-ng-repeat="lang in locale.langs" id="lang-{{lang}}" >
                        <a data-ng-click="locale.update(lang)" ng-class="{selected: locale.current === lang}" href=""></a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>