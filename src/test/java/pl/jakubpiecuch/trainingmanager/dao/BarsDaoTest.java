
package pl.jakubpiecuch.trainingmanager.dao;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;


public class BarsDaoTest extends AbstractBaseTest {
    
    @Autowired
    private BarsDao barsDao;

    @Test
    public void testFindAll() {
        assertEquals(1, barsDao.findAll().size());
    }
    
}
