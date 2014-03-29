package pl.jakubpiecuch.trainingmanager.service.calendar;

import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;


public class FullCalendarServiceTest extends AbstractBaseTest {
    
    @Autowired
    private CalendarService calendarService;
    
    @Test
    public void getEventsTest() {
        List<Event> events = calendarService.getEvents(1l);
        
        assertEquals(84, events.size());
    }

}
