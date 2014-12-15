'use strict';

MetronicApp.controller('DashboardController', function($rootScope, $scope, $http, $timeout) {


    // set sidebar closed and body solid layout mode
    $rootScope.settings.layout.pageBodySolid = true;
    $rootScope.settings.layout.pageSidebarClosed = false;
});