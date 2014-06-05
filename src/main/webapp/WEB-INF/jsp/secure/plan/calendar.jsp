<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="<c:url value="/resources/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css"/>" rel="stylesheet"/>
<script src="<c:url value="/resources/assets/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"/>"></script>
<script type="text/javascript">
    training.controller("calendarController", function($scope, $http) {
        $scope.eventSources = [];
        $scope.init = function() {
            var dragEventStartDate;
            var currentDate = new Date();
            currentDate.setHours(0,0,0,0);           
            $scope.uiConfig = {
                event: { zIndex: 999, revert: true, revertDuration: 0, appendTo: 'body', helper: 'clone', containment: 'window', scroll: true },
                eventBox : { axis: 'y', containment: '#calendar-row' },
                calendar: {
                    header: { left: 'title', center: '', right: 'prev, next,today,month,agendaWeek,agendaDay' },
                    editable: true,
                    disableResizing: true,
                    selectable: false,
                    droppable: true,
                    eventDragStart: function(event) { dragEventStartDate = new Date(event.start); },
                    eventDrop: function(event, dayDelta, minuteDelta, allDay, revertFunc) {
                        if (dragEventStartDate < currentDate || event.start < currentDate) {
                            revertFunc();
                        } else {
                            $http.post('${pageContext.servletContext.contextPath}' + '/api/calendar/event/move' , { id: event.id, start: moment(event.start).format('YYYY-MM-DD'), title: null, allDay: null }).error(function() { revertFunc(); });
                        }
                    },
                    eventDragStop: function(event, jsEvent, ui, view) {
                        var a = ui.offset;
                        var area = angular.element(document.querySelector('#calendar'));
                        var b = area.offset();
                        b.right = area.outerWidth() + b.left;
                        b.bottom = area.outerHeight() + b.top;
                        if (!(a.left >= b.left && a.top >= b.top && a.left <= b.right && a.top <= b.bottom)) {
                            $http.post('${pageContext.servletContext.contextPath}' + '/api/calendar/event/remove', { id: event.id }).success(function() {
                                $scope.calendar.fullCalendar('removeEvents', event.id);
                            });                    
                        }
                    },
                    drop: function(date, allDay, jsEvent, ui) {
                        $http.post('${pageContext.servletContext.contextPath}' + '/api/calendar/event/create', { id: parseInt(ui.helper.attr('value')), start: moment(date).format('YYYY-MM-DD'), title: null, allDay: null }).success(function(event) {
                            $scope.calendar.fullCalendar('renderEvent', event, true);
                            $scope.calendar.fullCalendar('refetchEvents');
                        }); 
                    },
                    dayClick: function(date) {
                        window.location.href = '${pageContext.servletContext.contextPath}' + '/plan/day.html?date=' + moment(date).format('YYYY-MM-DD');
                    }
                }
            };
            
            $http.get('${pageContext.servletContext.contextPath}' + '/api/calendar/events').success(function(data) { 
                $scope.eventSources[0] = data;
            });
            
            $http.get('${pageContext.servletContext.contextPath}' + '/api/dictionary/exercises').success(function(exercises) {
                $http.get('${pageContext.servletContext.contextPath}' + '/api/dictionary/partymuscles').success(function(pm) {
                    $scope.pm = pm;
                    $scope.exercises = {};
                    $scope.collapseStatus = [];
                    for(var i = 0; i < $scope.pm.length; i++) {
                        $scope.exercises[$scope.pm[i]] = [];
                        $scope.collapseStatus[i] = true;
                    }
                    for (var i = 0; i < exercises.length; i++) {
                    $scope.exercises[exercises[i].partyMuscles].push(exercises[i]);
                }
                });
            });
        };
        
        $scope.collapse = function(index) {
            $scope.collapseStatus[index] = !$scope.collapseStatus[index];
        };
    });
</script>
<style type="text/css">
    .fc-event-draggable {
        cursor: move;
    }
    
    .fc-day {
        cursor: pointer;
    }
    
    .perf-scroller {
        white-space: pre-line;
        width: 380px;
        height: 200px;
        overflow: hidden;
        position: relative;
    }
</style>
<div ng-controller="calendarController" ng-init="init()">
    <div class="portlet box blue calendar">
        <div class="portlet-title"><div class="caption"><i class="fa fa-reorder"></i><spring:message code="calendar.calendar"/></div></div>
        <div class="portlet-body light-grey">
            <div class="row" id="calendar-row">
                <div class="col-md-3 col-sm-12" data-drag="true" data-jqyoui-options="uiConfig.eventBox" jqyoui-draggable>
                    <h3 class="event-form-title"><spring:message code="exercises"/></h3>
                    <div id="external-events">            
                        <div>
                            <div class="fa fa-arrow-up" style="position: absolute; font-size: 200px; color: #ffb848; left: 25%; opacity: 0.3;top: 100px;"></div>
                            <div class="fa fa-arrow-down" style="position: absolute; font-size: 200px; color: #ffb848; left: 25%; opacity: 0.3;bottom: 100px;"></div>
                            <div ng-repeat="p in pm" class="portlet">
                                <div class="portlet-title" style="margin-bottom: 0px;">
                                    <div class="caption"><i class="fa fa-reorder"></i>{{p | translate }}</div>
                                    <div class="tools"><a ng-click="collapse($index)" ng-class="collapseStatus[$index] ? 'collapse' : 'expand'"></a></div>
                                </div>
                                <div class="portlet-body" collapse="collapseStatus[$index]">
                                   <perfect-scrollbar suppress-scroll-x="true" wheel-speed="5" class="perf-scroller">
                                       <div ng-repeat-start="e in exercises[p]" class="external-event label label-success" value="{{e.id}}" data-drag="true" data-jqyoui-options="uiConfig.event" jqyoui-draggable>{{e.name}}</div><br ng-repeat-end/>
                                   </perfect-scrollbar>
                                </div>
                            </div>
                        </div>
                    </div>                    
                </div>
                <div class="col-md-9 col-sm-12"><div id="calendar" ng-model="eventSources" ui-calendar="uiConfig.calendar" calendar="calendar" class="has-toolbar"></div></div>
            </div>
        </div>
    </div>
</div>


