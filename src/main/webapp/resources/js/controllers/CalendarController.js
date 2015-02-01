'use strict';

MetronicApp.controller('CalendarController', function($scope, $http, urlService, $filter, $location) {


    $scope.init = function() {
        $scope.calendar = {
            config: {
                calendar: {
                    header: {left: 'title', center: '', right: 'prev, next,today'},
                    disableResizing: true
                },
                viewRender: function(view, element) {
                    $http.get(urlService.apiURL('/users/1/workouts'), {params: {from: moment(view.start).format('YYYY-MM-DD'),  to: moment(view.end).format('YYYY-MM-DD')}}).then(function(data) {
                        var events = [];
                        for ( var i = 0; i < data.data.result.length; i++) {
                            var event = data.data.result[i];
                            console.log(event);
                            var title = [];
                            angular.forEach(event.muscles ,function(index) {
                                title.push($filter('translate')(index));
                            });
                            events.push({title: title.join(', '), date: event.date, allDay: true, id: event.id});
                        }
                        $scope.calendar.events[0] = events;
                    });
                },
                eventClick: function(event, jsEvent, view) {
                    $location.path('workout/' + event.id);
                }
            },
            events:[]
        };
    };
});