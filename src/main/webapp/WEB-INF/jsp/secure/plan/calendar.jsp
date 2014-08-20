<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="<c:url value="/resources/assets/global/plugins/fullcalendar/fullcalendar/fullcalendar.css"/>" rel="stylesheet"/>
<script src="<c:url value="/resources/assets/global/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"/>"></script>
<script type="text/javascript">
    training.controller("calendarController", function($scope, $http, $translate) {
        $scope.eventSources = [];

        $scope.startDrag = function(event, ui) {
            ui.helper.css('background-color', '#28b779');
            ui.helper.css('width', $('.exercise-event').width());
        };

        $scope.init = function() {
            $scope.page = 0;
            $translate.use('${pageContext.response.locale.language}');
            var dragEventStartDate;
            var currentDate = new Date();
            currentDate.setHours(0,0,0,0);           
            $scope.uiConfig = {
                draggable: {onStart: 'startDrag'},
                event: { zIndex: 999, revert: true, revertDuration: 0, appendTo: 'body', helper: 'clone' },
                eventBox : { axis: 'y', containment: '#calendar-row' },
                calendar: {
                    header: { left: 'title', center: '', right: 'prev, next,today' },
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
                                $scope.eventSources[0].splice($scope.eventSources[0].indexOf(event), 1);
                                var counter = 1;
                                for (var i = 0; i < $scope.eventSources[0].length; i++) {
                                var current = $scope.eventSources[0][i];
                                    if (moment(event.start).format('YYYY-MM-DD') === moment(current.start).format('YYYY-MM-DD') && event.id !== current.id) {
                                        current.title = (counter++) + '.' + current.title.split('.')[1];
                                    }
                                }
                            });                    
                        }
                    },
                    drop: function(date, allDay, jsEvent, ui) {
                        $http.post('${pageContext.servletContext.contextPath}' + '/api/calendar/event/create', { id: parseInt(ui.helper.attr('value')), start: moment(date).format('YYYY-MM-DD'), title: null, allDay: null }).success(function(event) {
                            $scope.eventSources[0].push(event);
                            $scope.calendar.fullCalendar('renderEvent', event, true);                 
                        }); 
                    },
                    dayClick: function(date) {
                        window.location.href = '${pageContext.servletContext.contextPath}' + '/plan/day.html?date=' + moment(date).format('YYYY-MM-DD');
                    },
                    eventClick: function(event, jsEvent, view) {
                        window.location.href = '${pageContext.servletContext.contextPath}' + '/plan/day.html?date=' + moment(event.start).format('YYYY-MM-DD') + '&position=' + event.title.split('.')[0];
                    },
                    viewRender: function(view, element) {
                       $http.get('${pageContext.servletContext.contextPath}' + '/api/calendar/event/start/' + moment(view.visStart).format('YYYY-MM-DD') + '/end/' + moment(view.visEnd).format('YYYY-MM-DD')).success(function(data) {
                            $scope.eventSources[0] = data;
                       }); 
                    }
                }
            };

            $http.get('${pageContext.servletContext.contextPath}' + '/api/dictionary/partymuscles').success(function(pm) {
                $scope.partyMuscles = pm;
            });
        };

        $scope.update = function(increment) {
            var maxResults = 8;
            $scope.page += increment;
            $http.get('${pageContext.servletContext.contextPath}' + '/api/dictionary/exercise/first/'+$scope.page * maxResults +'/max/'+maxResults+'/partymuscles/' + $scope.p).success(function(exercises) {
                $scope.exercises = exercises.result;
             });
        }
    });
</script>
<style type="text/css">


    .fc-day {
        cursor: pointer;
    }
    
    .exercise-event {
        width: 100%;
        cursor: pointer !important;
        text-overflow: ellipsis;
        overflow: hidden;
        margin-bottom: 0px;
        margin-left: 0px;
    }
    
    .exercise-event:hover {
        background-color: #28b779;
    }

    .exercise-events {
        position: fixed;
        z-index: 10;
        background-color: white;
        border: 2px solid #35aa47;
        margin-left: 10px;
        width: 24.5%;
        min-width: 300px
    }

    #external-events {
        margin-top: 10px;
    }
</style>
<div ng-controller="calendarController" ng-init="init()">
    <div class="portlet box grey-cascade calendar">
        <div class="portlet-title"><div class="caption"><i class="fa fa-calendar"></i><spring:message code="calendar.calendar"/></div></div>
        <div class="portlet-body light-grey">
            <div class="row" id="calendar-row">
                <div class="col-md-3 exercise-events">
                    <h3 class="event-form-title"><spring:message code="exercises"/></h3>
                    <select ng-model="p" ng-options="pm | translate for pm in partyMuscles" ng-change="update(0)" class="form-control"></select>
                    <div id="external-events">
                          <div ng-repeat-start="e in exercises" class="exercise-event external-event label label-success" value="{{e.id}}" data-drag="true" data-jqyoui-options="uiConfig.event" jqyoui-draggable="uiConfig.draggable">{{e.names['${pageContext.response.locale.language}']}}</div><br ng-repeat-end/>
                    </div>
                    <ul class="pager ng-cloak">
                        <li ng-show="page > 0" class="previous">
                            <a ng-click="update(-1)" href="">&laquo; <spring:message code="prevPage"/></a>
                        </li>
                        <li class="next">
                            <a ng-click="update(1)" href=""><spring:message code="nextPage"/> &raquo;</a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-12">
                    <div class="col-md-3"></div>
                    <div class="col-md-9"><div id="calendar" ng-model="eventSources" ui-calendar="uiConfig.calendar" calendar="calendar" class="has-toolbar"></div></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/browser.js"/>"></script>


