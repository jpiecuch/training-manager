'use strict';

app.controller('ChartController', function($scope, accountRecordService, chartService, $rootScope) {

    
    $scope.init = function(formName, chartName, type) {
        $scope.chart = {
            id: chartName
        };

        $scope.record = {
            name: formName,
            type: type
        };

        accountRecordService.count({to: moment().add(1, 'days').format('YYYY-MM-DD'), type: [type]}).then(function(count) {
            accountRecordService.retrieve({to: moment().add(1, 'days').format('YYYY-MM-DD'), type: [type], firstResult: count.data.count - 50, maxResults: 50}).then(function(data) {

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