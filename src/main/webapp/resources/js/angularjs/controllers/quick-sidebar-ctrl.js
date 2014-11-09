training.controller("QuickSidebarCtrl", function($scope, urlService, $location) {
    $scope.quickSidebar = {
        isVisibe: false,
        toggle: function() {
            this.isVisible = !this.isVisible;
        },
        tab: {
            tab: 'information',
            isActive: function(tab) {
                return tab === this.tab;
            },
            select: function(tab) {
                this.tab = tab;
            }
        }
    }
});