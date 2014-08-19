<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    training.controller("exercisesController", function($scope, $http, contextPath, ngTableParams, $filter, $modal, $sce, $translate) {

        $scope.resetForm = function() {
            $scope.exercise = {
                names: { pl: '', en: '' },
                movieUrl: ''
            };
        };

        $scope.init = function() {
            $translate.use('${pageContext.response.locale.language}');

            $scope.tableParams = new ngTableParams({
                page: 1,
                count: 10
            }, {
                getData: function($defer, params) {
                    var first = ((params.page() - 1) * params.count());
                    var max = params.count();
                    $http.get(contextPath + "/api/dictionary/exercise/first/" + first + "/max/" + max).success(function(data) {
                        var orderedData = params.sorting() ? $filter('orderBy')(data.result, params.orderBy()) : data.result;
                        $defer.resolve(orderedData);
                        params.lastPage = first + max >= data.count;
                        params.firstPage = params.page() === 1;
                    });
                }
            });
            
            $http.get(contextPath + "/api/dictionary/partymuscles").success(function(data) {
                $scope.partyMuscles = data;
            });

            $scope.resetForm();
        };

        $scope.save = function() {
            $http.post(contextPath + "/api/dictionary/exercise", $scope.exercise).success(function (data) {
            });
        };
        
        $scope.details = function(e) {
            $scope.exercise = e;
        }
    });
</script>
<div ng-controller="exercisesController" ng-init="init()">
    <script type="text/ng-template" id="custom/pager">
        <ul class="pager ng-cloak">
            <li class="previous" ng-show="!params.firstPage">
                <a ng-click="params.page(params.page() - 1)" href="">&laquo; <spring:message code="prevPage"/> </a>
            </li>
            <li class="next" ng-if="!params.lastPage">
                <a ng-click="params.page(params.page() + 1)" href=""><spring:message code="nextPage"/> &raquo;</a>
            </li>
            <li>
                <div class="btn-group">
                    <button type="button" ng-class="{'active':params.count() == 10}" ng-click="params.count(10)" class="btn btn-default">10</button>
                    <button type="button" ng-class="{'active':params.count() == 25}" ng-click="params.count(25)" class="btn btn-default">25</button>
                    <button type="button" ng-class="{'active':params.count() == 50}" ng-click="params.count(50)" class="btn btn-default">50</button>
                    <button type="button" ng-class="{'active':params.count() == 100}" ng-click="params.count(100)" class="btn btn-default">100</button>
                </div>
            </li>
        </ul>
    </script>
    <div class="row">
        <div class="col-md-6 ">
            <div class="portlet box light-grey">
                <div class="portlet-body">
                    <table class="table table-striped table-bordered table-hover" ng-table="tableParams" template-pagination="custom/pager">
                        <thead><tr>
                                <th width="55%" class="sortable" ng-class="{ 'sort-asc': tableParams.isSortBy('name', 'asc'), 'sort-desc': tableParams.isSortBy('name', 'desc') }" ng-click="tableParams.sorting({'name' : tableParams.isSortBy('name', 'asc') ? 'desc' : 'asc'})"><div><spring:message code="exercise.name"/></div></th>
                                <th width="30%" class="sortable" ng-class="{ 'sort-asc': tableParams.isSortBy('partyMuscles', 'asc'), 'sort-desc': tableParams.isSortBy('partyMuscles', 'desc') }" ng-click="tableParams.sorting({'partyMuscles' : tableParams.isSortBy('partyMuscles', 'asc') ? 'desc' : 'asc'})"><div><spring:message code="exercise.partyMuscles"/></div></th>
                                <th width="15%" ></th></tr></thead>
                        <tbody>
                            <tr ng-repeat="e in $data" class="odd gradeX">
                                <td style="vertical-align: middle" sortable="'name'">{{e.names['${pageContext.response.locale.language}']}}</td>
                                <td sortable="'partyMuscles'"><a href="<c:url value="/exercises/preview.html?partyMuscles={{e.partyMuscles}}"/>" class="btn btn-link">{{e.partyMuscles | translate}}</a></td>
                                <td style="vertical-align: middle; text-align: center"><i ng-if="exercise.id === e.id" class="fa fa-arrow-right" style="font-size: 20px; color: #35aa47"></i> <i ng-if="exercise.id === e.id" class="fa fa-arrow-right" style="font-size: 20px; color: #35aa47"></i> <i ng-if="exercise.id === e.id" class="fa fa-arrow-right" style="font-size: 20px; color: #35aa47"></i><a ng-if="exercise.id !== e.id" ng-click="details(e)" class="btn btn-info btn-xs"><i class="fa fa-edit"></i> <spring:message code="exercise.details"/></a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-6 form">
            <form class="form-horizontal" ng-submit="save()" novalidate role="form">
                <div class="form-body">
                    <div class="form-group" ng-repeat="(key, value) in exercise.names">
                        <label class="col-md-3 control-label"><spring:message code="exercise.name"/> {{key | translate}}</label>
                        <div class="col-md-9"><input ng-model="exercise.names[key]" type="text" class="form-control"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="exercise.partyMuscles"/></label>
                        <div class="col-md-9"><select ng-model="exercise.partyMuscles" ng-options="pm | translate for pm in partyMuscles" class="form-control input-medium"></select></div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="exercise.description"/></label>
                        <div class="col-md-9">
                            <textarea ng-model="exercise.decription" class="form-control" rows="3"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"><spring:message code="exercise.video"/></label>
                        <div class="col-md-9">
                            <input ng-model="exercise.movieUrl" type="text" class="form-control">
                            <div ng-show="exercise.movieUrl">
                            <youtube-video id="video" video-url="exercise.movieUrl" style="margin-top: 15px; width: 100%"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-actions fluid" style="background-color: #ffffff; border: none; margin-top: 0px">
                    <div class="col-md-offset-3 col-md-9" style="border-top: 1px solid #e5e5e5">
                        <button class="btn green" type="submit" style="margin-top: 20px;">OK</button>
                        <button class="btn default" ng-click="resetForm()" type="reset" style="margin-top: 20px;">Cancel</button>
                    </div>
                </div>
            </form>
        </div>
    </div>  
</div>