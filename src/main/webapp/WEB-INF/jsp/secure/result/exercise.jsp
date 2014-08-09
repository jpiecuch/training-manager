<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->

<!-- Head BEGIN -->
<head>
    <meta charset="utf-8">
    <title>Metronic One Page</title>

    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <meta content="Metronic Shop UI description" name="description">
    <meta content="Metronic Shop UI keywords" name="keywords">
    <meta content="keenthemes" name="author">
    <meta http-equiv="cleartype" content="on">

    <meta property="fb:app_id" content="1444314535848485">
    <meta property="og:title" content="Sample Workout">
    <meta property="og:image" content="https://fbstatic-a.akamaihd.net/images/devsite/attachment_blank.png">
    <meta property="og:url" content="http://www.gym-home.com${requestScope['javax.servlet.forward.request_uri']}">
    <meta property="og:type" content="gym-home:workout">

    <link rel="shortcut icon" href="favicon.ico">

    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700|Pathway+Gothic+One|PT+Sans+Narrow:400+700|Source+Sans+Pro:200,300,400,600,700,900&amp;subset=all" rel="stylesheet" type="text/css">

    <link href="<c:url value="/resources/assets/global/plugins/bootstrap/css/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/global/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">

    <!-- Theme styles BEGIN -->
    <link href="<c:url value="/resources/assets/global/css/components.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/frontend/onepage/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/frontend/onepage/css/style-responsive.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/frontend/onepage/css/themes/red.css"/>" rel="stylesheet" id="style-color">
    <link href="<c:url value="/resources/assets/frontend/onepage/css/custom.css"/>" rel="stylesheet">

    <!-- Theme styles END -->
</head>
<body ng-app ng-controller="ResultController" ng-init="init()">

<div class="services-block content content-center" id="services">
    <div class="container">
        <h2><spring:message code="exercise"/> : <strong>{{result.exercise.name}}</strong></h2>
        <h4>{{result.date | date: 'dd-MM-yyyy'}}</h4>
        <div class="row">
            <div class="col-md-6 col-sm-6 col-xs-12 item">
                <h3><spring:message code="equipment.loads"/> </h3>
                <div class="ab-cirlce ab-cirle-blue" style="position: static;">
                    <strong style="font-size: 28px; padding-top: 24px;">{{result.totalWeight}} kg</strong>
                </div>
            </div>
            <div class="col-md-6 col-sm-6 col-xs-12 item">
                <h3><spring:message code="equipment.equipment"/> </h3>
                <ul class="list-unstyled" style="margin-left: 180px;">
                    <li ng-repeat="n in result.necks"><div class="col-md-4 equipment-name"><spring:message code="equipment.neck"/>:</div> <div class="col-md-8 equipment-desc">{{n.type}}, {{n.lengthOf}} {{n.lengthOfUnit.shortName}}</div></li>
                    <li ng-repeat="b in result.bars"><div class="col-md-4 equipment-name"><spring:message code="equipment.bar"/>:</div> <div class="col-md-8 equipment-desc"><spring:message code="equipment.handles"/> - {{b.handlesNo}}</div></li>
                    <li ng-repeat="b in result.benches"><div class="col-md-4 equipment-name"><spring:message code="equipment.bench"/>:</div> <div class="col-md-8 equipment-desc">{{b.type}}</div></li>
                    <li ng-repeat="d in result.dumbbells"><div class="col-md-4 equipment-name"><spring:message code="equipment.dumbbell"/>:</div> <div class="col-md-8 equipment-desc">{{d.lengthOf}} {{d.lengthOfUnit.shortName}}</div></li>
                    <li ng-repeat="s in result.stands"><div class="col-md-4 equipment-name"><spring:message code="equipment.stand"/>:</div> <div class="col-md-8 equipment-desc"><spring:message code="equipment.levels"/> - {{s.levels}}</div></li>
                    <li ng-repeat="p in result.press"><div class="col-md-4 equipment-name"><spring:message code="equipment.press_singular"/>:</div> <div class="col-md-8 equipment-desc"><spring:message code="equipment.handles"/> - {{p.handlesNo}}</div></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="facts-block content content-center" id="facts-block">
    <div class="container">
        <div class="row" ng-repeat="r in rows" style="margin-bottom: 15px;">
            <div ng-repeat="s in r" class="col-md-{{col(r.length)}} col-sm-{{col(r.length)}} col-xs-6">
                <div class="item">
                    <strong>{{s.value}}</strong>
                    <spring:message code="exercise.series_singular"/> {{$index + 1}}
                </div>
            </div>

        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/angularjs/angular.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/app.js"/>" type="text/javascript" ></script>
<script type="text/javascript">
    function ResultController($scope, $http) {
        $scope.init = function() {
            var uri = '${requestScope['javax.servlet.forward.request_uri']}'.split('/');
            $http.get('<c:url value="/api/exercise/result/"/>' + uri[uri.length - 1]).success(function(data) {
                $scope.result = data;
                var mod4 = $scope.result.series.length % 4;
                var mod3 = $scope.result.series.length % 3;
                var chunkSize = mod4 % 4 === 0 ? 4 : mod3 % 3 === 0 ? 3 : $scope.result.series.length < 3 ? $scope.result.series.length : mod4 > mod3 ? 4 : 3;
                $scope.rows = $scope.result.series.map( function(e,i){
                    return i%chunkSize===0 ? $scope.result.series.slice(i,i+chunkSize) : null;
                }).filter(function(e){ return e; });
            });
        };

        $scope.col = function(length) {
            return length === 2 ? 6 : length === 1 ? 12 : length === 3 ? 4 : 3;
        };
    };
</script>
</body>
</html>
