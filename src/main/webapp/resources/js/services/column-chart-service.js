app.service('columnChartService', function() {
    this.get = function(data, lang, id) {

        return AmCharts.makeChart( id, {
            id: id,
            language: lang,
            "type": "serial",
            "theme": "light",
            "dataProvider": data,
            "valueAxes": [ {
                "gridColor": "#FFFFFF",
                "gridAlpha": 0.2,
                "dashLength": 0
            } ],
            "gridAboveGraphs": true,
            "startDuration": 1,
            "graphs": [ {
                "balloonText": "[[category]]: <b>[[value]]</b>",
                "fillAlphas": 0.8,
                "lineAlpha": 0.2,
                "type": "column",
                "valueField": "value"
            } ],
            "chartCursor": {
                "categoryBalloonEnabled": false,
                "cursorAlpha": 0,
                "zoomable": false
            },
            "categoryField": "key",
            "categoryAxis": {
                "gridPosition": "start",
                "gridAlpha": 0,
                "labelRotation": 45
            },
            "export": {
                "enabled": true
            }

        } );
    };
});