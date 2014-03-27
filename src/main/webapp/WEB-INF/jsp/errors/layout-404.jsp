<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <script src="<c:url value='/resources/assets/plugins/jquery-1.8.3.min.js'/>" type="text/javascript"></script>					
        <script>
            $(document).ready(function() {
                $('#path').val($(location).attr('pathname'));
                var $action = $('#form-submit').attr('action');
                $('#form-submit').removeAttr('action');
                $('#form-submit').attr('action', $action + '?path=' + $(location).attr('pathname'));
                $("#form-submit").submit();
            });
        </script>
    </head>
    <body>
        <form id="form-submit" method="POST" action="<c:url value="/404.html"/>"/>
    </body>
</html>