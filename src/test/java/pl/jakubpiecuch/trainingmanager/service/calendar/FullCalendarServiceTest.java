package pl.jakubpiecuch.trainingmanager.service.calendar;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.domain.Users;


public class FullCalendarServiceTest extends AbstractBaseTest {
    
    @Autowired
    private CalendarService calendarService;
    
    @Test
    public void getEventsTest() {
        List<Event> events = calendarService.events(new Users(1l, 1l));
        
        assertEquals(84, events.size());
    }

}
