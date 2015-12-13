'use strict';

app.controller('PlansController', function($scope, planService, tableService) {

    $scope.init = function() {
        $scope.table = tableService.get(planService);
        $scope.table.changePage(0);
    };

    $scope.removeRow = function(id) {
        planService.delete(id).then(function() {
            $scope.table.changePage(0);
        })
    }

});