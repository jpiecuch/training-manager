'use strict';

app.controller('PlanController', function($scope, $stateParams, dictionaryService, inputValidateService, planService, planStarterService) {
    $scope.validate = inputValidateService;

    $scope.init = function(form) {
        $scope.dictionary = {
            weekDays: [0,1,2,3,4,5,6]
        };

        dictionaryService.retrieve(1).then(function(data) {
            $scope.dictionary.goals = data;
        });

        dictionaryService.retrieve(2).then(function(data) {
            $scope.dictionary.muscles = data;
        });

        dictionaryService.retrieve(3).then(function(data) {
            $scope.dictionary.type = data;
        });

        dictionaryService.retrieve(4).then(function(data) {
            $scope.dictionary.level = data;
        });

        dictionaryService.retrieve(5).then(function(data) {
            $scope.dictionary.mechanics = data;
        });

        dictionaryService.retrieve(6).then(function(data) {
            $scope.dictionary.force = data;
        });

        planService.get(form, 0, $stateParams.id).then(function(data) {
            $scope.starter = planStarterService.get(data.id);
            $scope.plan = data;
            if ($scope.plan.phases.length === 0) {
                $scope.plan.addPhase();
            }
        });
    };

    /*$scope.$watch('planForm', function(form) {
        if ($scope.plan === undefined && form !== undefined) {
            planService.get(form, 0, $stateParams.id).then(function(data) {
                $scope.plan = data;
                $scope.starter = planStarterService.get($scope.plan.id);
                if ($scope.plan.phases.length === 0) {
                    $scope.plan.addPhase();
                }
            });

        }
    });*/
});

