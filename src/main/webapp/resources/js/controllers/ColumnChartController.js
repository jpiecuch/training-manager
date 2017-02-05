'use strict';

app.controller('ColumnChartController', function($scope, readService, columnChartService, queryValueService, $rootScope) {

    
    $scope.init = function(chartName, type, data) {
        $scope.chart = {
            id: chartName
        };
        
        readService[type].group(queryValueService.query(data.query)).then(function(data) {
            var chartData = [];
            for(var field in data.data) {
                chartData.push({key: field, value: data.data[field]});
            }
            angular.extend($scope.chart, columnChartService.get(chartData, $rootScope.settings.lang, chartName));
            $scope.$on('changeLang', function(event, args) {
                $scope.chart.language = args.lang;
                $scope.chart.validateData();
            });
        });
    };
});