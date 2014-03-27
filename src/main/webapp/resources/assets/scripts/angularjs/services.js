training.service('calendarService', function($http) {
    this.createCalendar = function(placeholderId) {
        $http.get("/TRAINING-MANAGER/api/calendar/events").success(function(data) {
            var dragEventStartDate;
            var date = new Date();
            date.setHours(0,0,0,0);
            $('#' + placeholderId).fullCalendar({
                header: {
                        left: 'title',
                        center: '',
                        right: 'prev, next,today,month,agendaWeek,agendaDay'
                },
                editable: true,
                disableResizing: true,
                selectable: false,
                droppable: true,
                eventDragStart: function(event) {
                    dragEventStartDate = new Date(event.start);
                },
                eventDrop: function(event, dayDelta, minuteDelta, allDay, revertFunc) {
                    var eventStart = $.fullCalendar.parseDate(event.start);
                    if (dragEventStartDate < date || eventStart < date) {
                        revertFunc();
                    } else {
                        $http.post("/TRAINING-MANAGER/api/calendar/event/move", { id: event.id, start: moment(event.start).format('YYYY-MM-DD'), title: null, allDay: null }).error(function() {
                            revertFunc();
                        });
                    }
                },
                eventDragStop: function(event, jsEvent, ui, view) {
                    var x = isElemOverDiv(ui, $('div.delete-events'));
                    if (!x) {
                        $http.post("/TRAINING-MANAGER/api/calendar/event/remove", { id: event.id }).success(function() {
                            $('#' + placeholderId).fullCalendar('removeEvents', event.id);
                        });                    
                    }
                },
                drop: function(date) {
                    $http.post("/TRAINING-MANAGER/api/calendar/event/create", { id: parseInt($(this).attr('value')), start: moment(date).format('YYYY-MM-DD'), title: null, allDay: null }).success(function(event) {
                        var copiedEventObject = $.extend(event, $(this).data('eventObject'));
                        $('#' + placeholderId).fullCalendar( 'renderEvent' , copiedEventObject, true);
                        $('#' + placeholderId).fullCalendar( 'refetchEvents' );
                    }); 
                },
                dayClick: function(date) {
                    window.location.href = '/TRAINING-MANAGER/plan/day.html?date=' + moment(date).format('YYYY-MM-DD');
                },
                events: data
            });
        });
    };
    
    var isElemOverDiv = function(draggedItem, dropArea) {
        var a = draggedItem.offset;
        var b = dropArea.offset();
        b.right = dropArea.outerWidth() + b.left;
        b.bottom = dropArea.outerHeight() + b.top;
        return a.left >= b.left && a.top >= b.top && a.left <= b.right && a.top <= b.bottom;
    };
}).service('dayService', function() {
    this.totalWeight = function(day) {
        var result = 0;
        for (var i = 0; i < day.dumbbells.length; i++) {
            result += day.dumbbells[i].weight;
        }
        for (var i = 0; i < day.loads.length; i++) {
            result += day.loads[i].weight;
        }
        for (var i = 0; i < day.necks.length; i++) {
            result += day.necks[i].weight;
        }
        return result;
    };
});

