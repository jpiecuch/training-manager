app.service('queryValueService', function() {

    this.resolve = function(entry) {
        return this[entry.type.toLowerCase()](entry.value);
    };

    this.query = function(data) {
        var query = {};
        for (var prop in data) {
            query[prop] = this.resolve(data[prop]);
        }
        return query
    };

    this.date = function(value) {
        if ('TODAY' === value) {
            return moment(new Date()).format('YYYY-MM-DD');
        }
        return moment(value).format('YYYY-MM-DD');
    };

    this.array = function(value) {
        var result = [];
        for(var i=0; i < value.length; i++) {
            var entry = value[i];
            result.push(this[entry.type.toLowerCase()](entry.value));
        }
        return result
    };

    this.number = function(value) {
        return value;
    };

    this.string = function(value) {
        return value;
    };
});