package pl.jakubpiecuch.trainingmanager.service.calendar;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface CalendarService {
    
    Event create(Event event, Users user) throws Exception;
    List<Event> events(Users user);
    void move(Event event) throws Exception;
    void remove(Event event);  
}
