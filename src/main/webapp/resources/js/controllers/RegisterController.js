'use strict';

app.controller('RegisterController', function($scope, registerService) {

    $scope.form = {};

    $scope.init = function(form) {
        $scope.register = registerService.get();
        $scope.register.init(form);
    };
});