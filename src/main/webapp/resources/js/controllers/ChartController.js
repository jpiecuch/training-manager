'use strict';

app.controller('ChartController', function($scope, accountRecordService, chartService, $rootScope, queryValueService) {

    
    $scope.init = function(formName, chartName, data) {
        $scope.chart = {
            id: chartName
        };

        $scope.record = {
            name: formName,
            type: data.dataType
        };
        var query = queryValueService.query(data.query);

        accountRecordService.count(query).then(function(count) {
            accountRecordService.retrieve(angular.extend(query, {maxResults: 50, firstResult: count.data.count - 50})).then(function(data) {

                angular.extend($scope.chart, chartService.get(data.data.result, $rootScope.settings.lang, chartName));
                $scope.$on('changeLang', function(event, args) {
                    $scope.chart.language = args.lang;
                    $scope.chart.validateData();
                });
            });
        });

        angular.extend($scope.record, accountRecordService.get(formName, $scope.record.type));
    };
});