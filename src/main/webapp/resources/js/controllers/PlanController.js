'use strict';

MetronicApp.controller('PlanController', function($scope, dictionaryService, inputValidateService, planService) {
    $scope.validate = inputValidateService;


    $scope.init = function(form) {
        $scope.dictionary = {
            weekDays: [0,1,2,3,4,5,6]
        };

        dictionaryService.retrieve(1).then(function(data) {
            $scope.dictionary.goals = data.data;
        });

        dictionaryService.retrieve(2).then(function(data) {
            $scope.dictionary.muscles = data.data;
        });

        dictionaryService.retrieve(3).then(function(data) {
            $scope.dictionary.type = data.data;
        });

        dictionaryService.retrieve(4).then(function(data) {
            $scope.dictionary.level = data.data;
        });

        dictionaryService.retrieve(5).then(function(data) {
            $scope.dictionary.mechanics = data.data;
        });

        dictionaryService.retrieve(6).then(function(data) {
            $scope.dictionary.force = data.data;
        });

    }

    $scope.$watch('planForm', function(form) {
        if ($scope.plan === undefined && form !== undefined) {
            planService.get(form, 0).then(function(data) {
                $scope.plan = data;
                if ($scope.plan.phases.length === 0) {
                    $scope.plan.addPhase();
                }
            });

        }
    });
});