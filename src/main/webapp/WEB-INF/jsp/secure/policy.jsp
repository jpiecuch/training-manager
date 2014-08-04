<div ng-controller="policyController" ng-init="init()"></div>
<script type="text/javascript">
    training.controller("policyController", function($scope, $http, $modal) {
        
        $scope.init = function() {   
            if ('${param.privacy}') {
                open();
            }
        };
        
        $scope.$on('showPolicyEvent', function () {
            open();
        }); 
            
        var open = function() {
            var modalInstance = $modal.open({
                templateUrl: 'policy.html',
                controller: policyModalController,
                size: 'lg'
            });
            modalInstance.result.then(function () {});
        }
        
    });
    
    var policyModalController = function () {};
</script>
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