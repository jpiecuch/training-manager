app.service('termsService', function($rootScope, $modal) {
    this.show = function() {
        $rootScope.$broadcast('showTermsEvent');
    };

    this.get = function() {
        return {
            init: function(scope) {
                scope.$on('showTermsEvent', function() {
                    var modalInstance = $modal.open({
                        animation: true,
                        templateUrl: 'termsModal.html',
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