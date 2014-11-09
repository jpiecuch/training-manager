<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div ng-controller="planController" ng-init="init()">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-8">
            <div ng-repeat="d in weekdays" class="col-md-12">
                <div class="portlet solid blue">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="fa fa-gift"></i>{{d}}
                        </div>
                        <div class="tools">
                            <a href="javascript:;" class="collapse"></a>
                            <a href="#portlet-config" data-toggle="modal" class="config"></a>
                            <a href="javascript:;" class="reload"></a>
                            <a href="javascript:;" class="remove"></a>
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div ng-repeat="e in plan[d]"> {{e.position}}. {{e.exercise.names['pl']}}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
