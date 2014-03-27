<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
    training.controller("exercisesController", function($scope, $http) {

        $scope.init = function() {
            $http.get("/TRAINING-MANAGER/api/dictionary/exercises").success(function(data) {
                $scope.exercises = data;
            });
        };    
    });
</script>
<div ng-controller="exercisesController" ng-init="init()">
<div class="row">
    <div class="col-md-12">
        <div class="portlet box light-grey">
            <div class="portlet-title"><div class="caption"><i class="fa fa-globe"></i>Managed Table</div></div>
            <div class="portlet-body">
                <div class="table-toolbar"><div class="btn-group"><button id="sample_editable_1_new" class="btn green">Add New <i class="fa fa-plus"></i></button></div></div>
                <table class="table table-striped table-bordered table-hover" id="sample_1">
                    <thead><tr><th>ID</th><th>Nazwa</th><th>Partia mięśni</th><th></th></tr></thead>
                    <tbody>
                        <tr ng-repeat="e in exercises" class="odd gradeX">
                            <td>{{e.id}}</td>
                            <td>{{e.name}}</td>
                            <td><a href="<c:url value="/exercises/preview.html?partyMuscles={{e.partyMuscles}}"/>" class="btn btn-link">{{e.partyMuscles}}</a></td>
                            <td><a href="<c:url value="/exercises/details.html?id={{e.id}}"/>" class="btn btn-info">Szczegóły</a></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
			
    
</div>