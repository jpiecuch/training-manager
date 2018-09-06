app.service('pieChartService', function() {
    this.get = function(data, lang, id) {

        return AmCharts.makeChart( id, {
            id: id,
            language: lang,
            "type": "pie",
            "theme": "light",
            "dataProvider": data,
            "valueField": "value",
            "titleField": "key",
            "balloon":{
                "fixedPosition":true
            },
            "export": {
                "enabled": true
            }
        } );
    };
});