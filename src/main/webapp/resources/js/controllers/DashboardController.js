'use strict';

app.controller('DashboardController', function($scope, userWorkoutService, accountRecordService, $location, $filter, $rootScope) {
    $scope.series = ['Weight [kg]'];
    $scope.labels = [];
    $scope.data = [];

    $scope.dashboard = {
        goToWorkout: function(id) {
            $location.url('/workout/' +id);
        }
    };

    $scope.init = function() {
         userWorkoutService.retrieve({from: moment(new Date()).format('YYYY-MM-DD'), maxResults: 5}).then(function(data) {
             $scope.dashboard.nextWorkouts = data.data;
         });

        accountRecordService.retrieve({type: ['WEIGHT'], maxResults: 100}).then(function(data) {

            var chart = AmCharts.makeChart("chartdiv", {
                language: $rootScope.settings.lang,
                "type": "serial",
                "theme": "light",
                fontFamily: "'Open Sans', sans-serif",
                "marginTop":0,
                "marginRight": 80,
                "dataProvider": data.data.result,
                "valueAxes": [{
                    "axisAlpha": 0,
                    "position": "left"
                }],
                "graphs": [{
                    "id":"g1",
                    "balloonText": "[[category]]<br><b><span style='font-size:14px;'>[[value]]</span></b>",
                    "bullet": "round",
                    "bulletSize": 8,
                    "lineColor": "#d1655d",
                    "lineThickness": 2,
                    "negativeLineColor": "#637bb6",
                    "type": "smoothedLine",
                    "valueField": "value"
                }],
                "chartScrollbar": {
                },
                "chartCursor": {
                    "cursorAlpha": 0,
                    "valueLineEnabled":true,
                    "valueLineBalloonEnabled":true,
                    "valueLineAlpha":0.5,
                    "fullWidth":true
                },
                "categoryField": "date",
                "categoryAxis": {
                    "parseDates": true,
                    "axisColor": "#DADADA",
                    "dashLength": 1,
                    "minorGridEnabled": true
                },
                "export": {
                    "enabled": true
                }
            });

            $scope.$on('changeLang', function(event, args) {
                chart.language = args.lang;
                chart.validateData();
            });


            var labels = [];
            var values = [];
            for (var i = 0; i < data.data.result.length; i++) {
                labels.push(i%2 === 0 ? $filter('date')(data.data.result[i].date,'dd-MM-yyyy') : '');
                values.push(parseFloat(data.data.result[i].value));
            }
            $scope.labels = labels;
            $scope.data.push(values);
        });
    }



});