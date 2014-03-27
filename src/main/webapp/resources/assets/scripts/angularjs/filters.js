training.filter('split', function() {
    return function(input, delimiter) {
        console.log(input);
        return input.split(delimiter);
    };
});