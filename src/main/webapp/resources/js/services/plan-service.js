MetronicApp.service('planService', function(phaseService, formValidateService) {
    var FORM_INPUT_NAME = "planName";
    var FORM_INPUT_GOAL = "planGoal";

    this.isValid = function(plan) {
        var result = plan.phases.length > 0;
        for (var i = 0; i < plan.phases.length; i++) {
            var phase = plan.phases[i];
            if (!phaseService.isValid(phase)) {
                result = false;
            }
        }
        plan.valid = result && this.isValidInputs(plan);
        return plan.valid;
    }

    this.isValidInputs = function(plan) {
        return plan.form[FORM_INPUT_NAME] !== undefined
            && plan.form[FORM_INPUT_NAME].$touched
            && plan.form[FORM_INPUT_NAME].$valid
            && plan.form[FORM_INPUT_GOAL] !== undefined
            && plan.form[FORM_INPUT_GOAL].$touched
            && plan.form[FORM_INPUT_GOAL].$valid
    }

    this.get = function(form, index) {
        var me = this;
        return {
            form: form,
            index: index,
            name: null,
            goal: null,
            phases: [],
            isValid: function() {
                return me.isValid(this);
            },
            create: function(form) {
                formValidateService.validate(form);
                if (this.isValid()) {
                    console.log('submit');
                }
            },
            addPhase: function() {
                this.phases.push(phaseService.get(this.form, '' + this.index + this.phases.length));
            }
        }
    }
});