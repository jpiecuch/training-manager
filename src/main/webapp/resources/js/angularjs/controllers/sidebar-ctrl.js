training.controller("SidebarCtrl", function($scope, urlService, $location) {
    $scope.menu = {
        elements: [
            {
                url: urlService.url('#/'),
                code: 'dashboard',
                icon: 'icon-home'
            },
            {
                url: urlService.url('#/calendar'),
                code: 'calendar.calendar',
                icon: 'icon-calendar'
            },
            {
                url: urlService.url('#/exercises'),
                code: 'exercises',
                icon: 'icon-doc'
            }
        ],
        isActive: function(url) {
            return url.substring(url.indexOf('#') + 1) === $location.path();
        },
        isClosed: true,
        state: function() {
            this.isClosed = !this.isClosed;
        }
    }

    $scope.hide = true;

    $scope.sidebar = function() {
        $scope.hide = !$scope.hide;
    }
});