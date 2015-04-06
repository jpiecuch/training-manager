'use strict';

app.controller('EquipmentController', function($scope, $stateParams, dictionaryService, inputValidateService, equipmentService) {
    $scope.validate = inputValidateService;

    $scope.dictionary = {};

    $scope.init = function() {
        dictionaryService.retrieve(8).then(function(data) {
            $scope.dictionary.types = data;
        });

        dictionaryService.retrieve(9).then(function(data) {
            $scope.dictionary.neckTypes = data;
        });
    }

    $scope.$watch('form', function(form) {
        if ($scope.equipment === undefined && form !== undefined) {
            equipmentService.get(form, $stateParams.id).then(function(data) {
                $scope.equipment = data;
            });
        }
    });
});