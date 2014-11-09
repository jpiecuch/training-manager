training.service('alertService', function($alert, alertOptions, $filter) {
    this.show = function(params) {
        $alert(angular.extend({}, alertOptions, {title: $filter('translate')(params.title), content: $filter('translate')(params.description), type: params.type}));
    }
});