
package pl.jakubpiecuch.trainingmanager.dao;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.domain.Calendars;


public class CalendarsDaoTest extends AbstractBaseTest {
    
    @Autowired
    private CalendarsDao calendarsDao;

    @Test
    public void testFindByUser() {
        Calendars calendar = calendarsDao.findByUser(1l, null);
        assertEquals("Kalendarz", calendar.getName());
        assertEquals("Jakub", calendar.getUser().getFirstName());
    }
    
}
