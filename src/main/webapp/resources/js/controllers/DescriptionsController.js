'use strict';

app.controller('DescriptionsController', function($scope, descriptionService, tableService) {

    $scope.init = function() {
        $scope.table = tableService.get(descriptionService);
        $scope.table.changePage(0);
    }

});