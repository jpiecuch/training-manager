'use strict';

app.controller('PolicyController', function($scope, policyService) {

    $scope.init = function() {
        $scope.policy = policyService.get();
        $scope.policy.init($scope);
    };
});