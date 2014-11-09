<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--[if lt IE 9]>
<script src="<c:url value="/resources/assets/global/plugins/respond.min.js"/>"></script>
<script src="<c:url value="/resources/assets/global/plugins/excanvas.min.js"/>"></script> 
<![endif]-->
<script src="<c:url value="/resources/assets/global/plugins/jquery-1.11.0.min.js"/>" type="text/javascript"></script>

<script src="<c:url value="/resources/js/angularjs/angular.js"/>" type="text/javascript" ></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.16/angular-route.js"></script>
<script src="//code.angularjs.org/1.2.16/angular-animate.min.js" data-semver="1.2.23"></script>
<script src="//code.angularjs.org/1.2.16/angular-sanitize.min.js" data-semver="1.2.23"></script>
<script src="<c:url value="/resources/js/angularjs/app.js"/>" type="text/javascript" ></script>


<script src="<c:url value="/resources/plugins/moment/moment-with-langs.min.js"/>"></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-cookies.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-sanitize.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/ui-bootstrap-tpls-0.10.0.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/calendar.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/perfect-scrollbar.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-dragdrop.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/directives/extrenal/checklist-model.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-strap.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-strap.tpl.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-translate.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-translate-loader-url.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-translate-loader-static-files.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-translate-storage-local.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-translate-storage-cookie.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/ng-table.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/directives/extrenal/angular-backstretch.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/angular-youtube-embed.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/textAngular.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/modules/external/textAngular-sanitize.min.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/directives/directives.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/filters/filters.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/services/services.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/services/authenticate-service.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/services/policy-service.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/services/resource-service.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/services/input-validate-service.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/services/alert-service.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/controllers/controllers.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/controllers/sidebar-ctrl.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/controllers/locale-ctrl.js"/>" type="text/javascript" ></script>
<script src="<c:url value="/resources/js/angularjs/controllers/quick-sidebar-ctrl.js"/>" type="text/javascript" ></script>

<script src="<c:url value="/resources/plugins/perfect-scrollbar/perfect-scrollbar-0.4.9.with-mousewheel.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/assets/global/plugins/jquery-migrate-1.2.1.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/assets/global/plugins/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/plugins/jquery-numeric/jquery.numeric.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/assets/global/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/lodash/lodash.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/browser.js"/>"></script>
<script src="<c:url value="/resources/plugins/flot/jquery.flot-0.7.js"/>"></script>
<script src="<c:url value="/resources/assets/global/plugins/flot/jquery.flot.time.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/flot/jquery.flot.tooltip.min.js"/>"></script>
