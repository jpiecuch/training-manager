package pl.jakubpiecuch.trainingmanager.service.calendar;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.Calendars;

public interface CalendarService {
    
    Calendars getCalendar(String name);
    Event create(Event event, Long userId) throws Exception;
    List<Event> getEvents(Long userId);
    void move(Event event) throws Exception;
    void remove(Event event);  
}
