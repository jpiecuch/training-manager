'use strict';

app.controller('DescriptionController', function($scope, $stateParams, descriptionService, dictionaryService, inputValidateService, videoProviderService) {
    $scope.validate = inputValidateService;

    $scope.videoProvider = videoProviderService;

    $scope.dictionary = {};

    $scope.init = function() {
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

        dictionaryService.retrieve(7).then(function(data) {
            $scope.dictionary.langs = data;
        });
    }

    $scope.$watch('form', function(form) {
        if ($scope.description === undefined && form !== undefined) {
            descriptionService.get(form, $stateParams.id).then(function(data) {
                $scope.description = data;
            });
        }
    });
});