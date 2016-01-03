'use strict';

app.controller('CalendarController', function($scope, $http, urlService, $filter, $location, userWorkoutService) {


    $scope.init = function() {
        $scope.calendar = {
            config: {
                calendar: {
                    header: {left: 'title', center: '', right: 'prev, next,today'},
                    disableResizing: true
                },
                viewRender: function(view) {
                    userWorkoutService.retrieve({from: moment(view.start).format('YYYY-MM-DD'),  to: moment(view.end).format('YYYY-MM-DD'), maxResults: 50}).then(function(data) {
                        var events = [];
                        for ( var i = 0; i < data.data.result.length; i++) {
                            var event = data.data.result[i];
                            var title = [];
                            angular.forEach(event.muscles ,function(index) {
                                title.push($filter('translate')(index));
                            });
                            events.push({title: title.join(', '), date: moment(event.date).format('YYYY-MM-DD'), allDay: true, id: event.id, color: userWorkoutService.state(event.state).color.code, borderColor: userWorkoutService.state(event.state).color.code});
                        }
                        $scope.calendar.events[0] = events;
                    });
                },
                eventClick: function(event) {
                    $location.path('workout/' + event.id);
                }
            },
            events:[]
        };
    };
});