<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    training.controller("exercisesController", function($scope, $http, contextPath, ngTableParams, $filter, $modal, $sce) {

        $scope.init = function() {
            $http.get(contextPath + "/api/dictionary/exercises").success(function(data) {
                $scope.tableParams = new ngTableParams({
                    page: 1,
                    count: 10
                }, {
                    total: data.length,
                    getData: function($defer, params) {
                        var orderedData = params.sorting() ?
                            $filter('orderBy')(data, params.orderBy()) :
                            data;

                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
               
                    }
                });
            });
            
            $http.get(contextPath + "/api/dictionary/partymuscles").success(function(data) {
                $scope.partyMuscles = data;
            });
            
            $scope.link = function(src) {
                var youtubeRegexp = /https?:\/\/(?:[0-9A-Z-]+\.)?(?:youtu\.be\/|youtube(?:-nocookie)?\.com\S*[^\w\s-])([\w-]{11})(?=[^\w-]|$)(?![?=&+%\w.-]*(?:['"][^<>]*>|<\/a>))[?=&+%\w.-]*/ig;
                var id = url.replace(youtubeRegexp, '$1');
                if (contains(id, ';')) {
                    var pieces = id.split(';');

                    if (contains(pieces[1], '%')) {
                        var uriComponent = decodeURIComponent(id.split(';')[1]);
                        id = ('http://youtube.com' + uriComponent)
                                .replace(youtubeRegexp, '$1');
                    } else {
                        id = pieces[0]
                    }
                } else if (contains(id, '#')) {
                    id = id.split('#')[0];
                }
                
                return $sce.trustAsResourceUrl('https://www.youtube.com/embed' + id);
            };
        };
        
        $scope.details = function(e) {
            $scope.selected = e;
            var modalInstance = $modal.open({
                    templateUrl: 'exercise.html',
                    controller: 'modalInstanceCtrl',
                    size: 'lg',
                    resolve: {
                        exercise: function() {
                            return angular.copy(e);
                        },
                        partyMuscles: function() {
                            return $scope.partyMuscles;
                        }
                    }
                });
            
            modalInstance.result.then(function (exercise) {
                $scope.selected.name = exercise.name;
                $scope.selected.partyMuscles = exercise.partyMuscles;
                $scope.selected.movieUrl = exercise.movieUrl;
                $scope.selected.description = exercise.description;
            });
        }
    }).controller("modalInstanceCtrl", function ($scope, $modalInstance, $sce, exercise, partyMuscles) {
        $scope.exercise = exercise;
        $scope.partyMuscles = partyMuscles;
        
        $scope.ok = function () {
          $modalInstance.close($scope.exercise);
        };
        
        $scope.link = function(url) {
            var youtubeRegexp = /https?:\/\/(?:[0-9A-Z-]+\.)?(?:youtu\.be\/|youtube(?:-nocookie)?\.com\S*[^\w\s-])([\w-]{11})(?=[^\w-]|$)(?![?=&+%\w.-]*(?:['"][^<>]*>|<\/a>))[?=&+%\w.-]*/ig;
                var id = url.replace(youtubeRegexp, '$1');
                if (contains(id, ';')) {
                    var pieces = id.split(';');

                    if (contains(pieces[1], '%')) {
                        var uriComponent = decodeURIComponent(id.split(';')[1]);
                        id = ('http://youtube.com' + uriComponent)
                                .replace(youtubeRegexp, '$1');
                    } else {
                        id = pieces[0]
                    }
                } else if (contains(id, '#')) {
                    id = id.split('#')[0];
                }
                return $sce.trustAsResourceUrl('https://www.youtube.com/embed/' + id);
        };
        
        function contains (str, substr) {
            return (str.indexOf(substr) > -1);
        }
    });
</script>
<div ng-controller="exercisesController" ng-init="init()">
    <script type="text/ng-template" id="exercise.html">
        <div class="modal-header">
            <h3 class="modal-title">I'm a modal!</h3>
        </div>
        <div class="modal-body">
            <form class="form-horizontal">
                <div class="form-body">
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="exercise.name"/></label>
                        <div class="col-md-9"><input ng-model="exercise.name" type="text" class="form-control input-inline input-medium"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="exercise.partyMuscles"/></label>
                        <div class="col-md-9"><select ng-model="exercise.partyMuscles" ng-options="pm | translate for pm in partyMuscles" class="form-control input-medium"/></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="exercise.movieUrl"/></label>
                        <div class="col-md-9"><input ng-model="exercise.movieUrl" type="text" class="form-control"></div>
                    </div>
                    <div class="form-group">
                        <iframe style="margin-left: 15px;" width="557" height="390" frameborder="0" video-url="{{exercise.movieUrl}}" id="video" class="ng-isolate-scope" allowfullscreen="1" title="YouTube video player" src="{{link(exercise.movieUrl)}}"></iframe>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="exercise.description"/></label>
                        <div class="col-md-9">
                            <textarea ng-model="exercise.decription" class="form-control" rows="3"></textarea>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="ok()">OK</button>
            <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
        </div>
    </script>
    <div class="row">
        <div class="col-md-12">
            <div class="portlet box light-grey">
                <div class="portlet-body">
                    <div class="table-toolbar"><div class="btn-group"><button id="sample_editable_1_new" class="btn green"><spring:message code="exercise.add"/> <i class="fa fa-plus"></i></button></div></div>
                    <table class="table table-striped table-bordered table-hover" ng-table="tableParams">
                        <thead><tr>
                                <th width="50%" class="sortable" ng-class="{ 'sort-asc': tableParams.isSortBy('name', 'asc'), 'sort-desc': tableParams.isSortBy('name', 'desc') }" ng-click="tableParams.sorting({'name' : tableParams.isSortBy('name', 'asc') ? 'desc' : 'asc'})"><div><spring:message code="exercise.name"/></div></th>
                                <th width="30%" class="sortable" ng-class="{ 'sort-asc': tableParams.isSortBy('partyMuscles', 'asc'), 'sort-desc': tableParams.isSortBy('partyMuscles', 'desc') }" ng-click="tableParams.sorting({'partyMuscles' : tableParams.isSortBy('partyMuscles', 'asc') ? 'desc' : 'asc'})"><div><spring:message code="exercise.partyMuscles"/></div></th>
                                <th width="20%" ></th></tr></thead>
                        <tbody>
                            <tr ng-repeat="e in $data" class="odd gradeX">
                                <td style="vertical-align: middle" sortable="'name'">{{e.name}}</td>
                                <td sortable="'partyMuscles'"><a href="<c:url value="/exercises/preview.html?partyMuscles={{e.partyMuscles}}"/>" class="btn btn-link">{{e.partyMuscles | translate}}</a></td>
                                <td style="vertical-align: middle"><a ng-click="details(e)" class="btn btn-info btn-xs"><i class="fa fa-edit"></i> <spring:message code="exercise.details"/></a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>  
</div>