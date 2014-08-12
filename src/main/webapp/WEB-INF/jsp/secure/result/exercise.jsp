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
    <title><spring:message code="app.title"/></title>

    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <meta content="gym-home.com" name="description"/>
    <meta content="Jakub Piecuch" name="author"/>
    <meta content="gym, training" name="keywords">
    <meta http-equiv="cleartype" content="on">

    <spring:eval var="fbAppId" expression="@propertyConfigurer.getProperty('facebook.clientId')" />
    <meta property="fb:app_id" content="${fbAppId}">
    <meta property="og:title" content="${exercise.exercise.name}">
    <meta property="og:image" content="https://fbstatic-a.akamaihd.net/images/devsite/attachment_blank.png">
    <meta property="og:url" content="http://www.gym-home.com${requestScope['javax.servlet.forward.request_uri']}">
    <meta property="og:type" content="gym-home:workout">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700|Pathway+Gothic+One|PT+Sans+Narrow:400+700|Source+Sans+Pro:200,300,400,600,700,900&amp;subset=all" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/assets/global/plugins/bootstrap/css/bootstrap.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/global/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/global/css/components.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/frontend/onepage/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/frontend/onepage/css/style-responsive.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/assets/frontend/onepage/css/themes/red.css"/>" rel="stylesheet" id="style-color">
    <link href="<c:url value="/resources/assets/frontend/onepage/css/custom.css"/>" rel="stylesheet">
</head>
<body>

<div class="services-block content content-center" id="services">
    <div class="container">
        <h2><spring:message code="exercise"/> : <strong>${exercise.exercise.name}</strong></h2>
        <h4>${exercise.date}</h4>
        <div class="row">
            <div class="col-md-6 col-sm-6 col-xs-12 item">
                <h3><spring:message code="equipment.loads"/> </h3>
                <div class="ab-cirlce ab-cirle-blue" style="position: static;">
                    <strong style="font-size: 28px; padding-top: 24px;">${exercise.totalWeight} kg</strong>
                </div>
            </div>
            <div class="col-md-6 col-sm-6 col-xs-12 item">
                <h3><spring:message code="equipment.equipment"/> </h3>
                <ul class="list-unstyled" style="margin-left: 180px;">
                    <c:forEach items="${exercise.necks}" var="n"> <li><div class="col-md-4 equipment-name"><spring:message code="equipment.neck"/>:</div> <div class="col-md-8 equipment-desc">${n.type}, ${n.lengthOf} ${n.lengthOfUnit.shortName}</div></li></c:forEach>
                    <c:forEach items="${exercise.bars}" var="b"><li><div class="col-md-4 equipment-name"><spring:message code="equipment.bar"/>:</div> <div class="col-md-8 equipment-desc"><spring:message code="equipment.handles"/> - ${b.handlesNo}</div></li></c:forEach>
                    <c:forEach items="${exercise.benches}" var="b"><li><div class="col-md-4 equipment-name"><spring:message code="equipment.bench"/>:</div> <div class="col-md-8 equipment-desc">${b.type}</div></li></c:forEach>
                    <c:forEach items="${exercise.dumbbells}" var="d"><li><div class="col-md-4 equipment-name"><spring:message code="equipment.dumbbell"/>:</div> <div class="col-md-8 equipment-desc">${d.lengthOf} ${d.lengthOfUnit.shortName}</div></li></c:forEach>
                    <c:forEach items="${exercise.stands}" var="s"><li><div class="col-md-4 equipment-name"><spring:message code="equipment.stand"/>:</div> <div class="col-md-8 equipment-desc"><spring:message code="equipment.levels"/> - ${s.levels}</div></li></c:forEach>
                    <c:forEach items="${exercise.press}" var="p"><li><div class="col-md-4 equipment-name"><spring:message code="equipment.press_singular"/>:</div> <div class="col-md-8 equipment-desc"><spring:message code="equipment.handles"/> - ${p.handlesNo}</div></li></c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="facts-block content content-center" id="facts-block">
    <div class="container">
        <c:forEach items="${rows}" var="r" varStatus="rStatus">
            <div class="row" style="margin-bottom: 15px;">
                <c:set var="rowLength" value="${fn:length(r)}"/>
                <c:set var="column" value="${rowLength eq 2 ? 6 : rowLength eq 1 ? 12 : rowLength eq 3 ? 4 : 3}"/>
                <c:forEach items="${r}" var="s" varStatus="sStatus">
                    <div class="col-md-${column} col-sm-${column} col-xs-6">
                        <div class="item">
                            <strong>${s.value}</strong>
                            <spring:message code="exercise.series_singular"/> ${(rStatus.count - 1) * chunk + sStatus.count}
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
