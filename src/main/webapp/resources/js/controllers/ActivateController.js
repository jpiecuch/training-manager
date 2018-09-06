'use strict';

app.controller('ActivateController', function($scope, activateService, $stateParams) {

    $scope.init = function() {
        activateService.activate($stateParams.code);
    };
});