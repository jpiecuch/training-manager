app.service('activateService', function($http, urlService, alertService, $timeout, $state) {
    this.activate = function(code) {

        return $http.post(urlService.apiURL('/activate'), code).then(function success() {
            alertService.show({type: 'success', title: 'success', description: 'user.activate'});
            $timeout(function(){
                $state.go('index');
            }, 5000);
        }, function error(response) {
            alertService.show({type: 'warning', title: 'warning', description: response.data.message});
            $timeout(function(){
                $state.go('index');
            }, 5000);
        });
    };
});