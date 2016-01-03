'use strict';

app.controller('PlanController', function($scope, $stateParams, dictionaryService, planService, planStarterService, inputValidateService) {
    $scope.validate = inputValidateService;

    $scope.init = function() {
        $scope.dictionary = {};
        $scope.dictionary.weekDays = [0,1,2,3,4,5,6];
        $scope.dictionary.goals = dictionaryService.get(1);
        $scope.dictionary.muscles = dictionaryService.get(2);
        $scope.dictionary.type = dictionaryService.get(3);
        $scope.dictionary.level = dictionaryService.get(4);
        $scope.dictionary.mechanics = dictionaryService.get(5);
        $scope.dictionary.force = dictionaryService.get(6);

        planService.get($stateParams.id).then(function(data) {
            $scope.starter = planStarterService.get(data.id);
            $scope.plan = data;
            if ($scope.plan.phases.length === 0) {
                $scope.plan.addPhase();
                $scope.plan.activatePhase($scope.plan.phases[0]);
            }
            if (!$scope.plan.id) {
                $scope.plan.addRelation($scope.starter, 'planId');
            }
        });
    };
});

