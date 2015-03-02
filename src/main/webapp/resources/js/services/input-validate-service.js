app.service('inputValidateService', function() {
    this.isValid = function(input) {
        if (input) {
            return input.$valid && input.$touched;
        }
    }

    this.isNotValid = function(input) {
        if (input) {
            return input.$invalid && input.$touched;
        }
    }

    this.status = function(input) {
        if (input) {
            return this.isNotValid(input) ? 'has-error' : this.isValid(input) ? 'has-success' : '';
        }
    }
});