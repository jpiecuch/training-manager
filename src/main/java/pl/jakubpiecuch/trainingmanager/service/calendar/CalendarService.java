package pl.jakubpiecuch.trainingmanager.service.calendar;

import java.util.Date;
import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface CalendarService {
    
    Event create(Event event, Users user) throws Exception;
    List<Event> events(Users user, Date start, Date end);
    void move(Event event) throws Exception;
    void remove(Event event);  
}
