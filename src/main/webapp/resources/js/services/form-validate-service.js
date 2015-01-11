MetronicApp.service('formValidateService', function() {
    this.validate = function(form) {
        angular.forEach($('form[name='+form.$name+']').find('input'), function(node){
            if (node.type !== 'submit' && node.name && form[node.name]) {
                form[node.name].$touched = true;
            }
        });
        angular.forEach($('form[name='+form.$name+']').find('select'), function(node){
            if (node.name) {
                form[node.name].$touched = true;
            }
        });
        angular.forEach($('form[name='+form.$name+']').find('textarea'), function(node){
            if (node.name) {
                form[node.name].$touched = true;
            }
        });
    };
});