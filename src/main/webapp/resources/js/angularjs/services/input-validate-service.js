training.service('inputValidateService', function() {
    this.isValid = function(input) {
        return input.$valid && !input.$pristine
    }

    this.isNotValid = function(input) {
        return input.$invalid && !input.$pristine;
    }

    this.status = function(input) {
        return this.isNotValid(input) ? 'has-error' : this.isValid(input) ? 'has-success' : '';
    }
});