training.service('policyService', function($rootScope) {
    this.show = function() {
        $rootScope.$broadcast('showPolicyEvent');
    }
});