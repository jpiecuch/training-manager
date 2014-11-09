<div ng-controller="policyController" ng-init="init(${param.privacy})"></div>
<script type="text/javascript">
    var policyModalController = function () {};
</script>
<style type="text/css">
    .modal-content {
        margin-top: 80px;
    }
</style>
<script type="text/ng-template" id="policy.html">
    <div class="modal-header">
        <h3 class="modal-title"><spring:message code="privacy.policy"/></h3>
    </div>
    <div class="modal-body">
        <div class="note note-info">
            <h4 class="block"><spring:message code="collect.info.title"/></h4>
            <p><spring:message code="collect.info.content"/></p>
        </div>
        <div class="note note-info">
            <h4 class="block"><spring:message code="usage.information.title"/></h4>
            <p><spring:message code="usage.information.content"/></p>
        </div>
        <div class="note note-info">
            <h4 class="block"><spring:message code="usage.cookies.title"/></h4>
            <p><spring:message code="usage.cookies.content"/></p>
        </div>
        <div class="note note-info">
            <h4 class="block"><spring:message code="disclose.information.title"/></h4>
            <p><spring:message code="disclose.information.content"/></p>
        </div>
    </div>
</script>