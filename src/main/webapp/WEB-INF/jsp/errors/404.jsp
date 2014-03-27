<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="<c:url value='/resources/assets/css/pages/error.css" rel="stylesheet'/>" type="text/css"/>
<div class="row-fluid">
    <div class="span12">
        <div class="row-fluid page-404">
            <div class="span5 number">
                404
            </div>
            <div class="span7 details">
                <h3><spring:message code="message.opps.you.lost"/></h3>
                <p>
                    <spring:message code="message.we.can.not.find.page"/>
                </p>
            </div>
        </div>
    </div>
</div>