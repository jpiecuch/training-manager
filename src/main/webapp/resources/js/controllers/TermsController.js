'use strict';

app.controller('TermsController', function($scope, termsService) {

    $scope.init = function() {
        $scope.policy = termsService.get();
        $scope.policy.init($scope);
    };
});