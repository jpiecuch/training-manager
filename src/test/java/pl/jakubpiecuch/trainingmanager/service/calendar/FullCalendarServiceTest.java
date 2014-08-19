package pl.jakubpiecuch.trainingmanager.service.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.domain.Users;


public class FullCalendarServiceTest extends AbstractBaseTest {
    
    @Autowired
    private CalendarService calendarService;
    
    @Test
    public void getEventsTest() throws ParseException {
        List<Event> events = calendarService.events(new Users(1l, 1l), new SimpleDateFormat("yyyy-MM-dd").parse("2014-01-01"), new SimpleDateFormat("yyyy-MM-dd").parse("2014-05-01"), new Locale("pl"));
        
        assertEquals(6, events.size());
    }

}
