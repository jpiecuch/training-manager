app.service('policyService', function($rootScope, $modal) {
    this.show = function() {
        $rootScope.$broadcast('showPolicyEvent');
    };

    this.get = function() {
        return {
            init: function(scope) {
                scope.$on('showPolicyEvent', function() {
                    var modalInstance = $modal.open({
                        animation: true,
                        templateUrl: 'policyModal.html',
                        size: 'lg'
                    });

                    modalInstance.result.then(function (selectedItem) {

                    }, function () {

                    });
                });
            }
        }
    }
});