<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="<c:url value="/resources/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css"/>" rel="stylesheet"/>
<script src="<c:url value="/resources/assets/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"/>"></script>
<script type="text/javascript">
    training.controller("calendarController", function($scope, $http, $sce, calendarService) {
        $scope.changeShowState = function(property) {
            $scope.showExercises[property] = !$scope.showExercises[property];
        };
        
        $scope.init = function() {
            calendarService.createCalendar("calendar");
            $http.get("/TRAINING-MANAGER/api/dictionary/exercises").success(function(data) {
            $scope.exercises = {
                 abdominals: [],
                 backs: [],
                 biceps_and_flexors: [],
                 chest: [],
                 forearms: [],
                 legs_and_buttocks: [],
                 shoulders: [],
                 triceps_and_rectifiers: []
             };
            
            $scope.pm = [ 'abdominals', 'backs', 'biceps_and_flexors', 'chest', 'forearms', 'legs_and_buttocks', 'shoulders', 'triceps_and_rectifiers'];
            
            for (var i = 0; i < data.length; i++) {
                 switch(data[i].partyMuscles) {
                     case 'ABDOMINALS': 
                        $scope.exercises.abdominals.push(data[i]);
                        break;
                     case 'BACKS': 
                        $scope.exercises.backs.push(data[i]);
                        break;
                     case 'BICEPS_AND_FLEXORS': 
                        $scope.exercises.biceps_and_flexors.push(data[i]);
                        break;
                     case 'CHEST': 
                        $scope.exercises.chest.push(data[i]);
                        break;
                     case 'FOREARMS': 
                        $scope.exercises.forearms.push(data[i]);
                        break;
                     case 'LEGS_AND_BUTTOCKS': 
                        $scope.exercises.legs_and_buttocks.push(data[i]);
                        break;
                     case 'SHOULDERS': 
                        $scope.exercises.shoulders.push(data[i]);
                        break;
                     case 'TRICEPS_AND_RECTIFIERS': 
                        $scope.exercises.triceps_and_rectifiers.push(data[i]);
                        break;
                 }
             }
            });
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
</style>
<div ng-controller="calendarController" ng-init="init()">
    <div class="portlet box blue calendar">
        <div class="portlet-title"><div class="caption"><i class="fa fa-reorder"></i><spring:message code="calendar.calendar"/></div></div>
        <div class="portlet-body light-grey">
            <div class="row" id="calendar-row">
                <div class="col-md-3 col-sm-12" ng-vertical-draggable="#calendar-row">
                    <h3 class="event-form-title"><spring:message code="calendar.exercises"/></h3>
                    <div id="external-events">            
                        <div id="event_box">
                            <div class="fa fa-arrow-up" style="position: absolute; font-size: 200px; color: #ffb848; left: 25%; opacity: 0.3;top: 100px;"></div>
                            <div class="fa fa-arrow-down" style="position: absolute; font-size: 200px; color: #ffb848; left: 25%; opacity: 0.3;bottom: 100px;"></div>
                            <div ng-repeat="p in pm" class="portlet">
                                <div class="portlet-title">
                                    <div class="caption"><i class="fa fa-reorder"></i>{{p}}</div>
                                    <div class="tools"><a href="javascript:;" class="expand"></a></div>
                                </div>
                                <div class="portlet-body collapse">
                                   <div ng-scroller class="scroller" style="height:200px">
                                       <div ng-repeat-start="e in exercises[p]" class="external-event label label-success" value="{{e.id}}" ng-calendar-event>{{e.name}}</div><br ng-repeat-end/>
                                   </div>
                                </div>
                            </div>
                        </div>
                    </div>                    
                </div>
                <div class="col-md-9 col-sm-12"><div id="calendar" class="has-toolbar delete-events"></div></div>
            </div>
        </div>
    </div>
</div>


