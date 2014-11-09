training.service('dayService', function() {
    this.totalWeight = function(day) {
        var result = 0;
        for (var i = 0; i < day.dumbbells.length; i++) {
            result += day.dumbbells[i].weight;
        }
        for (var i = 0; i < day.loads.length; i++) {
            result += day.loads[i].weight;
        }
        for (var i = 0; i < day.necks.length; i++) {
            result += day.necks[i].weight;
        }
        return result;
    };
}).service('urlService', function(contextPath, version) {
    this.url = function(suffix) {
        return contextPath + '/' + suffix;
    }

    this.apiURL = function(suffix) {
        return this.url('api/' + version + suffix);
    }


});

