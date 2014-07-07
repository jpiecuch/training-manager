training.filter('stopwatch', function() {
    return function(input, minUnit, maxUnit) {

        var hours = Math.floor(input / 3600000);
        var minutes = Math.floor((input - hours * 3600000) / 60000);
        var seconds = Math.floor((input - minutes * 60000 - hours * 3600000 ) / 1000);
        var miliseconds = input - seconds * 1000 - minutes * 60000 - hours * 3600000;
        return (hours > 0 ? hours + 'h ' : '') + (minutes > 0 || hours > 0 ? minutes + 'm ' : '') + seconds + 's';
    };
});
;