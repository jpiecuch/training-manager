MetronicApp.service('inputValidateService', function() {
    this.isValid = function(input) {
        return input.$valid && input.$touched
    }

    this.isNotValid = function(input) {
        return input.$invalid && input.$touched;
    }

    this.status = function(input) {
        return this.isNotValid(input) ? 'has-error' : this.isValid(input) ? 'has-success' : '';
    }
});