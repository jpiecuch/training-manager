'use strict';

MetronicApp.controller('PlanController', function($rootScope, $scope, dictionaryService, descriptionService, inputValidateService, formValidateService, planService) {
    $scope.dictionary = {
        weekDays: [0,1,2,3,4,5,6]
    };

    $scope.validate = inputValidateService;

    $scope.descriptions = {
        retrieve: function(idx) {
            descriptionService.retrieve($scope.descriptions.params[idx]).then(function(data) {
                $scope.descriptions.result[idx] = data.data;
            });
        },
        result: {},
        params: {}
    }



    $scope.init = function(form) {
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
            $scope.plan = planService.get(form);
            $scope.plan.addPhase();
        }
    });
});