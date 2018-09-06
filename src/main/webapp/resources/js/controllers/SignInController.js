'use strict';

app.controller('SignInController', function($scope, signInService) {

    $scope.init = function(form) {
        $scope.signIn = signInService.get();
        $scope.signIn.init(form);
    };
});