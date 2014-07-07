 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div ng-controller="recordsController" ng-init="init()">
    <div class="col-md-9" >
        <div style="margin-bottom: 20px;" class="tabbable-custom nav-justified">
            <ul class="nav nav-tabs nav-justified"> <li ng-repeat="d in dayExercises" ng-class="{'active': d === tab}"><a href="" ng-click="changeTab(d)">{{d.position}}. {{d.exercise.partyMuscles | translate}}</a></li></ul>
            <div style="float: left; width: 100%" class="tab-content">
                <div  class="tab-pane col-md-12 active" id="tab">
                    <div class="col-md-6">
                        <div class="dashboard-stat blue">
                            <div class="visual visual-status"><i class="fa" ng-class="{'fa-check-square-o': tab.confirmed, 'fa-square-o': !tab.confirmed}"></i></div>
                            <div class="details" style="position: static;">
                                <div class="desc"><h4>{{tab.exercise.name}}</h4></div>
                                <div class="desc"><h5>{{tab.date | date: 'dd MMMM yyyy (EEEE)'}}</h5></div> 
                                <div class="number total-weight">{{tab.totalWeight}} kg</div>
                            </div>
                            <div class="more"><a data-toggle="modal" href="#dialog" class="btn btn-sm btn-info" ng-click="progress(tab)"><i class="fa fa-bar-chart-o"></i> <spring:message code="exercise.progress"/></a> <a href="" class="btn btn-sm btn-success" ng-click="save(tab)"><i class="fa fa-save"></i> <spring:message code="save"/></a> <a href="" class="btn btn-sm btn-warning" ng-click="confirm(tab)"><i class="fa fa-check"></i> <spring:message code="confirm"/></a></div>
			</div>
                        <div class="portlet">
                        <div class="portlet-title"><div class="caption"><spring:message code="equipment.equipment"/></div></div>
                        <div class="portlet-body">
                                <span ng-hide="tab.bars.length === 0" class="equipments-title label label-info"><input ng-click="checkAll(deleteEquipment, tab, 'bars')" type="checkbox" id="bars-all-checkbox-delete" ng-checked="deleteEquipment['bars'].length === tab['bars'].length"><label class="all-checkbox" for="bars-all-checkbox-delete"><span></span></label> <spring:message code="equipment.bars"/></span>
                                <div class="equipments-info" ng-hide="tab.bars.length === 0">
                                    <div ng-repeat="bar in tab.bars">
                                        <input type="checkbox" checklist-model="deleteEquipment.bars" checklist-value="bar" id="bars-checkbox-delete-{{$index}}"/><label for="bars-checkbox-delete-{{$index}}"><span></span></label>
                                        {{$index + 1}}. <spring:message code="equipment.strength"/>: <span>{{bar.strength}} {{bar.strengthUnit.shortName}}</span>, <spring:message code="equipment.length"/>: <span>{{bar.lengthOf}} {{bar.lengthOfUnit.shortName}}</span>, <spring:message code="equipment.handles"/>: <span>{{bar.handlesNo}}</span>
                                    </div>
                                </div>
                                <span ng-hide="tab.benches.length === 0" class="equipments-title label label-info"><input ng-click="checkAll(deleteEquipment, tab, 'benches')" type="checkbox" id="benches-all-checkbox-delete" ng-checked="deleteEquipment['benches'].length === tab['benches'].length"><label class="all-checkbox" for="benches-all-checkbox-delete"><span></span></label><spring:message code="equipment.benches"/></span>
                                <div ng-hide="tab.benches.length === 0" class="equipments-info">     
                                    <div ng-repeat="bench in tab.benches">
                                        <input type="checkbox" checklist-model="deleteEquipment.benches" checklist-value="bench" id="benches-checkbox-delete-{{$index}}"/><label for="benches-checkbox-delete-{{$index}}"><span></span></label>
                                        {{$index + 1}}. <spring:message code="equipment.type"/>: <span>{{bench.type}}</span>, <spring:message code="equipment.length"/>: <span>{{bench.lengthOf}} {{bench.lengthOfUnit.shortName}}</span>, <spring:message code="equipment.height"/>: <span>{{bench.height}} {{bench.heightUnit.shortName}}</span>
                                   </div> 
                                </div>
                                <span ng-hide="tab.dumbbells.length === 0" class="equipments-title label label-info"><input ng-click="checkAll(deleteEquipment, tab, 'dumbbells')" type="checkbox" id="dumbbells-all-checkbox-delete" ng-checked="deleteEquipment['dumbbells'].length === tab['dumbbells'].length"><label class="all-checkbox" for="dumbbells-all-checkbox-delete"><span></span></label><spring:message code="equipment.dumbbells"/></span>
                                <div ng-hide="tab.dumbbells.length === 0" class="equipments-info">
                                    <div ng-repeat="dumbbell in tab.dumbbells">
                                        <input type="checkbox" checklist-model="deleteEquipment.dumbbells" checklist-value="dumbbell" id="dumbbells-checkbox-delete-{{$index}}"/><label for="dumbbells-checkbox-delete-{{$index}}"><span></span></label>
                                        {{$index + 1}}. <spring:message code="equipment.weight"/>: <span>{{dumbbell.weight}} {{dumbbell.weightUnit.shortName}}</span>, <spring:message code="equipment.permanent.load"/>: <span>{{dumbbell.connectedLoad ? '<spring:message code="yes"/>' : '<spring:message code="no"/>'}}</span>
                                    </div>
                                </div>
                                <span ng-hide="tab.loads.length === 0" class="equipments-title label label-info"><input ng-click="checkAll(deleteEquipment, tab, 'loads')" type="checkbox" id="loads-all-checkbox-delete" ng-checked="deleteEquipment['loads'].length === tab['loads'].length"><label class="all-checkbox" for="loads-all-checkbox-delete"><span></span></label> <spring:message code="equipment.loads"/></span>
                                <div ng-hide="tab.loads.length === 0" class="equipments-info">
                                    <div ng-repeat="load in tab.loads">
                                        <input type="checkbox" checklist-model="deleteEquipment.loads" checklist-value="load" id="loads-checkbox-delete-{{$index}}"/><label for="loads-checkbox-delete-{{$index}}"><span></span></label>
                                        {{$index + 1}}. <spring:message code="equipment.weight"/>: <span>{{load.weight}} {{load.weightUnit.shortName}}</span>, <spring:message code="equipment.hole.diameter"/>: <span>{{load.holeDiameter}} {{load.holeDiameterUnit.shortName}}</span>
                                    </div>
                                </div>
                                <span ng-hide="tab.necks.length === 0" class="equipments-title label label-info"><input ng-click="checkAll(deleteEquipment, tab, 'necks')" type="checkbox" id="necks-all-checkbox-delete" ng-checked="deleteEquipment['necks'].length === tab['necks'].length"><label class="all-checkbox" for="necks-all-checkbox-delete"><span></span></label> <spring:message code="equipment.necks"/></span>
                                <div ng-hide="tab.necks.length === 0" class="equipments-info">
                                    <div ng-repeat="neck in tab.necks">
                                        <input type="checkbox" checklist-model="deleteEquipment.necks" checklist-value="neck" id="necks-checkbox-delete-{{$index}}"/><label for="necks-checkbox-delete-{{$index}}"><span></span></label>
                                        {{$index + 1}}. <spring:message code="equipment.type"/>: <span>{{neck.type.name}}</span>, <spring:message code="equipment.weight"/>: <span>{{neck.weight}} {{neck.weightUnit.shortName}}</span>, <spring:message code="equipment.diameter"/>: <span>{{neck.diameter}} {{neck.diameterUnit.shortName}}</span>, <spring:message code="equipment.length"/>: <span>{{neck.lengthOf}} {{neck.lengthOfUnit.shortName}}</span>
                                    </div>
                                </div>
                                <span ng-hide="tab.stands.length === 0" class="equipments-title label label-info"><input ng-click="checkAll(deleteEquipment, tab, 'stands')" type="checkbox" id="stands-all-checkbox-delete" ng-checked="deleteEquipment['stands'].length === tab['stands'].length"><label class="all-checkbox" for="stands-all-checkbox-delete"><span></span></label> <spring:message code="equipment.stands"/></span>
                                <div ng-hide="tab.stands.length === 0" class="equipments-info">                                 
                                    <div ng-repeat="stand in tab.stands">
                                        <input type="checkbox" checklist-model="deleteEquipment.stands" checklist-value="stand" id="stands-checkbox-delete-{{$index}}"/><label for="stands-checkbox-delete-{{$index}}"><span></span></label>
                                        {{$index + 1}}. <spring:message code="equipment.levels"/>: <span>{{stand.levels}}</span>, <spring:message code="equipment.height.min"/>: <span>{{stand.heightMin}} {{stand.heightMinUnit.shortName}}</span>, <spring:message code="equipment.height.max"/>: <span>{{stand.heightMax}} {{stand.heightMaxUnit.shortName}}</span>
                                    </div>     
                                </div>
                                <span ng-hide="tab.press.length === 0" class="equipments-title label label-info"><input ng-click="checkAll(deleteEquipment, tab, 'press')" type="checkbox" id="press-all-checkbox-delete" ng-checked="deleteEquipment['press'].length === tab['press'].length"><label class="all-checkbox" for="press-all-checkbox-delete"><span></span></label> <spring:message code="equipment.press"/></span>
                                <div ng-hide="tab.press.length === 0" class="equipments-info">                                 
                                    <div ng-repeat="press in tab.press">
                                        <input type="checkbox" checklist-model="deleteEquipment.press" checklist-value="press" id="press-checkbox-delete-{{$index}}"/><label for="press-checkbox-delete-{{$index}}"><span></span></label>
                                        {{$index + 1}}. <spring:message code="equipment.strength"/>: <span>{{press.strength}} {{press.strengthUnit.shortName}}</span>, <spring:message code="equipment.handles"/>: <span>{{press.handlesNo}}</span>
                                    </div>     
                                </div>
                                <a class="btn btn-danger btn-sm btn-block" ng-hide="empty(deleteEquipment)" ng-click="delete(tab)"><i class="fa fa-minus"></i> <spring:message code="equipment.remove"/></a>
                        </div>
                        </div>
                        <div class="portlet">
                            <div class="portlet-title"><div class="caption"><spring:message code="exercise.series"/></div></div>
                            <div class="portlet-body">
                                <div class="series-content">
                                    <div ng-repeat="s in tab.series" class="row-fluid"> 
                                        <div class="input-group input-small margin-top-10">
                                            <span class="input-group-addon" style="min-width: 45px;">{{$index +1}}.</span><input type="text" ng-model="s.value" class="form-control " style="width: 50px">
                                            <span class="input-group-btn"><a class="btn btn-sm default" type="button" style="font-size: 17px; min-width: 34px"><i class="fa fa-minus"></i></a></span>
                                        </div> 
                                    </div>
                                </div>
                                <div class="input-group input-small" style="margin-top: 10px">
                                    <span class="input-group-btn"><a href="" class="btn btn-sm green float-right" style="font-size: 17px; min-width: 34px; margin-right: -8px" ng-click="addSeries(d)"><i class="fa fa-plus"></i></a></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="portlet">
                            <div class="portlet-title"><div class="caption"><spring:message code="exercise.stopwatch"/></div></div>
                            <div class="portlet-body">
                                <div class="tiles">
                                    <div style="width: 100%;" class="tile double {{stopwatch[tab.id] ? 'bg-green-haze' : 'bg-blue-madison'}}" ng-click="stopwatch[tab.id] ? stop(tab) : start(tab)">
                                        <div class="tile-body"><div style="text-align: center; margin-top: 35px; font-size: xx-large">{{tab.time | stopwatch}}</div></div>
                                        <div class="tile-object"><div class="name"><i class="fa fa-clock-o"></i></div><div class="number"><i ng-class="stopwatch[tab.id] ?  'fa fa-pause' : 'fa fa-play'"></i></div></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="portlet">
                            <div class="portlet-title"><div class="caption"><spring:message code="exercise.video"/></div></div>
                            <div class="portlet-body" >
                                <span ng-if="tab.exercise.movieURL"><youtube-video id="video" video-url="tab.exercise.movieURL"/></span>
                                <div style="text-align: center;" ng-if="!tab.exercise.movieURL"><i style="opacity: 0.3; font-size: 300px; margin-top: 150px; color: #cecece;" class="fa fa-eye-slash"></i></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <h4><spring:message code="exercise.available.equipment"/></h4>
        <div id="external-events">
            <span ng-show="equipment.bars.length > 0" class="equipments-title label label-info"><input type="checkbox" id="bars-all-checkbox" ng-checked="addEquipment['bars'].length === equipment['bars'].length" ng-click="checkAll(addEquipment, equipment, 'bars')"><label class="all-checkbox" for="bars-all-checkbox"><span></span></label> <spring:message code="equipment.bars"/></span>
            <div ng-show="equipment.bars.length > 0" class="equipments-info">  
                <div ng-repeat="b in equipment.bars | orderBy: 'strength'">
                    <div style="margin-left: 0px;"><input type="checkbox" checklist-model="addEquipment.bars" checklist-value="b" id="bars-checkbox-{{$index}}"/><label for="bars-checkbox-{{$index}}"><span></span></label><spring:message code="equipment.strength"/>: <span>{{b.strength}} {{b.strengthUnit.shortName}}</span>, <spring:message code="equipment.length"/>: <span>{{b.lengthOf}} {{b.lengthOfUnit.shortName}}</span>, <spring:message code="equipment.handles"/>: <span>{{b.handlesNo}}</span></div>                  
                </div>
            </div>
            <span ng-show="equipment.benches.length > 0" class="equipments-title label label-info"><input type="checkbox" id="benches-all-checkbox" ng-checked="addEquipment['benches'].length === equipment['benches'].length" ng-click="checkAll(addEquipment, equipment, 'benches')"><label class="all-checkbox" for="benches-all-checkbox"><span></span></label> <spring:message code="equipment.benches"/></span>
            <div ng-show="equipment.benches.length > 0" class="row-fluid equipments-info">  
                <div ng-repeat="b in equipment.benches | orderBy: 'length'">
                    <div style="margin-left: 0px;"><input type="checkbox" checklist-model="addEquipment.benches" checklist-value="b" id="benches-checkbox-{{$index}}"/><label for="benches-checkbox-{{$index}}"><span></span></label> <spring:message code="equipment.type"/>: <span>{{b.type}}</span>, <spring:message code="equipment.length"/>: <span>{{b.lengthOf}} {{b.lengthOfUnit.shortName}}</span>, <spring:message code="equipment.height"/>: <span>{{b.height}} {{b.heightUnit.shortName}}</span></div>
                </div>
            </div>
            <span ng-show="equipment.dumbbells.length > 0" class="equipments-title label label-info"><input type="checkbox" id="dumbbells-all-checkbox" ng-checked="addEquipment['dumbbells'].length === equipment['dumbbells'].length" ng-click="checkAll(addEquipment, equipment, 'dumbbells')"><label class="all-checkbox" for="dumbbells-all-checkbox"><span></span></label>  <spring:message code="equipment.dumbbells"/></span>
            <div ng-show="equipment.dumbbells.length > 0" class="row-fluid equipments-info">  
                <div ng-repeat="d in equipment.dumbbells | orderBy: 'weight'">
                    <div style="margin-left: 0px;"><input type="checkbox" checklist-model="addEquipment.dumbbells" checklist-value="d" id="dumbbells-checkbox-{{$index}}"/><label for="dumbbells-checkbox-{{$index}}"><span></span></label> <spring:message code="equipment.weight"/>: <span>{{d.weight}} {{d.weightUnit.shortName}}</span>, <spring:message code="equipment.permanent.load"/>: <span>{{d.connectedLoad ? '<spring:message code="yes"/>' : '<spring:message code="no"/>'}}</span></div>
                </div>
            </div>
            <span ng-show="equipment.loads.length > 0" class="equipments-title label label-info"><input type="checkbox" id="loads-all-checkbox" ng-checked="addEquipment['loads'].length === equipment['loads'].length" ng-click="checkAll(addEquipment, equipment, 'loads')"><label class="all-checkbox" for="loads-all-checkbox"><span></span></label> <spring:message code="equipment.loads"/></span>
            <div ng-show="equipment.loads.length > 0" class="row-fluid equipments-info">  
                <div ng-repeat="l in equipment.loads | orderBy: 'weight'">
                    <div style="margin-left: 0px;" ><input type="checkbox" checklist-model="addEquipment.loads" checklist-value="l" id="loads-checkbox-{{$index}}"/><label for="loads-checkbox-{{$index}}"><span></span></label> <spring:message code="equipment.weight"/>: <span>{{l.weight}} {{l.weightUnit.shortName}}</span>, <spring:message code="equipment.hole.diameter"/>: <span>{{l.holeDiameter}} {{l.holeDiameterUnit.shortName}}</span></div>
                </div>
            </div>          
            <span ng-show="equipment.necks.length > 0" class="equipments-title label label-info"><input type="checkbox" id="necks-all-checkbox" ng-checked="addEquipment['necks'].length === equipment['necks'].length" ng-click="checkAll(addEquipment, equipment, 'necks')"><label class="all-checkbox" for="necks-all-checkbox"><span></span></label> <spring:message code="equipment.necks"/></span>
            <div ng-show="equipment.necks.length > 0" class="row-fluid equipments-info">  
                <div ng-repeat="n in equipment.necks | orderBy: 'weight'">
                    <div style="margin-left: 0px;"><input type="checkbox" checklist-model="addEquipment.necks" checklist-value="n" id="necks-checkbox-{{$index}}"/><label for="necks-checkbox-{{$index}}"><span></span></label> <spring:message code="equipment.type"/>: <span>{{n.type}}</span>, <spring:message code="equipment.weight"/>: <span>{{n.weight}} {{n.weightUnit.shortName}}</span>, <spring:message code="equipment.diameter"/>: <span>{{n.diameter}} {{n.diameterUnit.shortName}}</span>, <spring:message code="equipment.length"/>: <span>{{n.lengthOf}} {{n.lengthOfUnit.shortName}}</span></div>
                </div>
            </div>
            <span ng-show="equipment.stands.length > 0" class="equipments-title label label-info"><input type="checkbox" id="stands-all-checkbox" ng-checked="addEquipment['stands'].length === equipment['stands'].length" ng-click="checkAll(addEquipment, equipment, 'stands')"><label class="all-checkbox" for="stands-all-checkbox"><span></span></label> <spring:message code="equipment.stands"/></span>
            <div ng-show="equipment.stands.length > 0" class="row-fluid equipments-info">  
                <div ng-repeat="s in equipment.stands | orderBy: 'heightMin'">
                    <div style="margin-left: 0px;"><input type="checkbox" checklist-model="addEquipment.stands" checklist-value="s" id="stands-checkbox-{{$index}}"/><label for="stands-checkbox-{{$index}}"><span></span></label> <spring:message code="equipment.levels"/>: <span>{{s.levels}}</span>, <spring:message code="equipment.height.min"/>: <span>{{s.heightMin}} {{s.heightMinUnit.shortName}}</span>, <spring:message code="equipment.height.max"/>: <span>{{s.heightMax}} {{s.heightMaxUnit.shortName}}</span></div>
                </div>
            </div>
            <span ng-show="equipment.press.length > 0" class="equipments-title label label-info"><input type="checkbox" id="press-all-checkbox" ng-checked="addEquipment['press'].length === equipment['press'].length" ng-click="checkAll(addEquipment, equipment, 'press')"><label class="all-checkbox" for="press-all-checkbox"><span></span></label> <spring:message code="equipment.press"/></span>
            <div ng-show="equipment.press.length > 0" class="row-fluid equipments-info">  
                <div ng-repeat="p in equipment.press | orderBy: 'strength'">
                    <div style="margin-left: 0px;"><input type="checkbox" checklist-model="addEquipment.press" checklist-value="p" id="press-checkbox-{{$index}}"/><label for="presss-checkbox-{{$index}}"><span></span></label> <spring:message code="equipment.strength"/>: <span>{{p.strength}} {{p.strengthUnit.shortName}}</span>, <spring:message code="equipment.handles"/>: <span>{{p.handlesNo}}</span></div>
                </div>
            </div>
        </div>
        <a ng-hide="empty(addEquipment)" href="" class="btn btn-block btn-primary btn-sm" style="margin-top: 10px;" ng-click="add()"><i class="fa fa-plus" ></i> <spring:message code="add"/></a>
    </div>
</div>
<script src="<c:url value="/resources/plugins/flot/jquery.flot-0.7.js"/>"></script>
<script src="<c:url value="/resources/assets/global/plugins/flot/jquery.flot.time.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/flot/jquery.flot.tooltip.min.js"/>"></script>
<script type="text/javascript">
        training.controller("recordsController", function($scope, $http, $sce, $modal, $timeout, dayService, growl) {
            var chartColors = ['#88bbc8', '#ed7a53', '#9FC569', '#bbdce3', '#9a3b1b', '#5a8022', '#2c7282'];
            var equipment = {};
        $scope.link = function(src) {return $sce.trustAsResourceUrl(src);};

        $scope.addEquipment = { loads: [], bars: [], dumbbells: [], necks: [], stands: [], benches: [], press: [] };
        $scope.deleteEquipment = { loads: [], bars: [], dumbbells: [], necks: [], stands: [], benches: [], press: [] };
        
        $scope.stopwatch = [];
        
        $scope.init = function() {
            $scope.stopwatchFlag = false;
            $http.get('${pageContext.servletContext.contextPath}' + "/api/exercise/${param.date}").success(function(data) {
                var position = '${param.position}';
                $scope.date = ${param.date};
                $scope.dayExercises = data;
                $scope.tab = !isNaN(position) && parseInt(Number(position)) == position && position <= data.length && position > 0 ? data[position - 1] : data[0];
                $http.get('${pageContext.servletContext.contextPath}' + "/api/dictionary/equipment").success(function(data) {
                    equipment = data;
                    prepareTab();
                });
            });
        };
        
        $scope.changeTab = function(d) {
            $scope.tab = d;
            clear($scope.addEquipment);
            clear($scope.deleteEquipment);
            prepareTab();
        };
        
        function prepareTab() {
            $scope.equipment = angular.copy(equipment);
            updateArrays($scope.tab.loads, $scope.equipment.loads, null, false);
            updateArrays($scope.tab.bars, $scope.equipment.bars, null, false);
            updateArrays($scope.tab.dumbbells, $scope.equipment.dumbbells, null, false);
            updateArrays($scope.tab.necks, $scope.equipment.necks, null, false);
            updateArrays($scope.tab.stands, $scope.equipment.stands, null, false);
            updateArrays($scope.tab.benches, $scope.equipment.benches, null, false);
            updateArrays($scope.tab.press, $scope.equipment.press, null, false);
        }
        
        $scope.delete = function(d) {
            updateArrays($scope.deleteEquipment.loads, d.loads, $scope.equipment.loads, true);
            updateArrays($scope.deleteEquipment.bars, d.bars, $scope.equipment.bars, true);
            updateArrays($scope.deleteEquipment.dumbbells, d.dumbbells, $scope.equipment.dumbbells, true);
            updateArrays($scope.deleteEquipment.necks, d.necks, $scope.equipment.necks, true);
            updateArrays($scope.deleteEquipment.stands, d.stands, $scope.equipment.stands, true);
            updateArrays($scope.deleteEquipment.benches, d.benches, $scope.equipment.benches, true);
            updateArrays($scope.deleteEquipment.press, d.press, $scope.equipment.press, true);
            d.totalWeight = dayService.totalWeight(d);
        };
        
        $scope.add = function() {
            updateArrays($scope.addEquipment.loads, $scope.equipment.loads, $scope.tab.loads, true);
            updateArrays($scope.addEquipment.bars, $scope.equipment.bars, $scope.tab.bars, true);
            updateArrays($scope.addEquipment.dumbbells, $scope.equipment.dumbbells, $scope.tab.dumbbells, true);
            updateArrays($scope.addEquipment.necks, $scope.equipment.necks, $scope.tab.necks, true);
            updateArrays($scope.addEquipment.stands, $scope.equipment.stands, $scope.tab.stands, true);
            updateArrays($scope.addEquipment.benches, $scope.equipment.benches, $scope.tab.benches, true);
            updateArrays($scope.addEquipment.press, $scope.equipment.press, $scope.tab.press, true);
            $scope.tab.totalWeight = dayService.totalWeight($scope.tab, true);
        };
        
        $scope.addSeries = function(d) {
            d.series[d.series.length] = {};
        };
        
        $scope.save = function(d, message) {
            $http.post('${pageContext.servletContext.contextPath}' + "/api/exercise/save", d).success(function() {
                growl.addSuccessMessage(message === undefined ? "day.exercise.save" : message);
            });
        };
        
        $scope.confirm = function(d) {
            if ($scope.stopwatch[d.id]) {
                $scope.stop(d);
            }
            d.confirmed = true;
            $scope.save(d, "day.exercise.confirm");
        };
        
        $scope.show = function(array, object) {
            for (var i = 0; i < array.length; i++) {             
                if (array[i].id === object.id) {
                    return false;
                }
            }
            return true;
        };
        
        $scope.empty = function(e) {
            return e.loads.length === 0 && e.bars.length === 0 && e.dumbbells.length === 0 && e.necks.length === 0 && e.stands.length === 0 && e.benches.length === 0 && e.press.length === 0;
        };
        
        $scope.progress = function(d) {
            $modal.open({
              template: '<div class="modal-header"><button type="button" class="close" ng-click="ok()"></button><h4 class="modal-title"><spring:message code="exercise.progress"/></h4></div><div class="modal-body"><div class="progress-chart" style="height: 400px; width: 870px"></div></div>',
              controller: ModalInstanceCtrl,
              windowClass: 'progress-modal'
            });
            
            $http.get('${pageContext.servletContext.contextPath}' + "/api/exercise/"+ d.exercise.id +"/progress/").success(function(progressData) {
                var chartLine = new Array();
                var chartWeightLine = new Array();
                for (var i = 0; i < progressData.length; i++) {
                    var date = new Date(14400000 + progressData[i].date);//TODO shit idea 
                    var weight = progressData[i].totalWeight !== 0.0 ? progressData[i].totalWeight : 1.0;
                    var series = 0;
                    for(var j = 0; j < progressData[i].series.length; j++) {
                        series += parseInt(progressData[i].series[j].value);
                    }
                    chartWeightLine.push([date, weight]);
                    chartLine.push([date, weight * series / (12 * progressData[i].series.length)]);
                }
                
                var options = {
                    grid: { show: true, aboveData: true, color: "#3f3f3f", labelMargin: 5, axisMargin: 0, borderWidth: 0, borderColor: null,
                        minBorderMargin: 5, clickable: true, hoverable: true, autoHighlight: true, mouseActiveRadius: 20 },
                    series: {
                        grow: { active: false, stepMode: "linear", steps: 50, stepDelay: true },
                        lines: { show: true, fill: false, lineWidth: 4, steps: false },
                        points: { show: true,radius: 5, symbol: "circle", fill: true, borderColor: "#fff" }
                    },
                    legend: { position: "se" },
                    yaxis: { },
                    xaxis: { mode: "time", timeformat: "%d-%m-%Y", minTickSize: [1, "day"], timezone: "Europe/Warsaw" },
                    colors: chartColors,
                    shadowSize: 1,
                    tooltip: true,              
                    tooltipOpts: { content: "%x: %y.0", xDateFormat: "%d-%m-%Y", shifts: { x: -30, y: -50 } }
                };

                jQuery.plot(jQuery(".progress-chart"), [
                    {
                        label: "Postęp w ćwiczeniu (współczynnik)",
                        data: chartLine,
                        lines: { fillColor: "#f2f7f9" },
                        points: { fillColor: "#88bbc8" }
                    },
                    {
                        label: "Postęp w ćwiczeniu (waga)",
                        data: chartWeightLine,
                        lines: { fillColor: "#fff8f2" },
                        points: { fillColor: "#ed7a53" }
                    }
                ], options);  
                
            });
        };
        
        $scope.start = function (d) {
            $scope.stopwatch[d.id] = $timeout(function() {
                d.time += 1000;
                $scope.start(d);
            }, 1000);
        };

        $scope.stop = function (d) {
            $timeout.cancel($scope.stopwatch[d.id]);
            $scope.stopwatch[d.id] = null;
        };
        
        $scope.checkAll = function(checkObject, useObject, name) {
            if (checkObject[name].length === useObject[name].length) {
                checkObject[name] = [];
            } else {
                checkObject[name] = angular.copy(useObject[name]);
            }
        };
        
        function updateArrays(items, reducesArray, increasedArray, clear) {
            for(var i = 0; i < items.length; i++) {
                if (reducesArray !== null) {
                    reducesArray.splice(_.findIndex(reducesArray, {id : items[i].id}), 1);
                }
                if (increasedArray !== null) {
                    increasedArray.push(items[i]);
                }
            }
            if (clear) {
                items.length = 0;
            }
        }
        
        function clear(object) {
            object = { loads: [], bars: [], dumbbells: [], necks: [], stands: [], benches: [], press: [] };
        }
    });
    
    var ModalInstanceCtrl = function ($scope, $modalInstance) {
        $scope.ok = function () {
          $modalInstance.close();
    };
};
</script>