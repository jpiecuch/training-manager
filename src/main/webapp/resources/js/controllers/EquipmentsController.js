'use strict';

MetronicApp.controller('EquipmentsController', function($scope, equipmentService, tableService) {
    $scope.init = function() {
        $scope.table = tableService.get(equipmentService);
        $scope.table.changePage(0);
    }
});