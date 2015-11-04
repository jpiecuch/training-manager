app.service('inputService', function() {
    this.isValid = function(input) {
      return !input.touched ? undefined : (input.value !== undefined && 0 !== input.value.length);
    };

    this.get = function(value) {
        var me = this;
        return {
            value: value,
            touched:  false,
            isValid: function() {
                return me.isValid(this);
            },
            touch: function() {
                this.touched = true;
            },
            isTouched: function() {
                return this.touched;
            },
            errors: []
        }
    };

    this.getArray = function(value) {
        var me = this;
        var input = me.get(value);
        input.isValid = function() {
            var selected = false;
            for (var i = 0; i < this.value.length; i++) {
                if (this.value[i].selected) {
                    selected = true;
                    break;
                }
            }
            return !input.touched ? undefined : selected;
        };
        return input;
    }
});
