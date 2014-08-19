package pl.jakubpiecuch.trainingmanager.service.calendar;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface CalendarService {
    
    Event create(Event event, Users user, Locale locale) throws Exception;
    List<Event> events(Users user, Date start, Date end, Locale locale);
    void move(Event event) throws Exception;
    void remove(Event event);  
}
