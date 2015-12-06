'use strict';

app.controller('PasswordController', function($scope, passwordService) {

    $scope.init = function(form) {
        $scope.password = passwordService.get();
        $scope.password.init(form);
    };
});