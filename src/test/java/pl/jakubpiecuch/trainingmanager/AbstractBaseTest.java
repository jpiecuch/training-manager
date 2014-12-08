package pl.jakubpiecuch.trainingmanager;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "classpath:/applicationContext-security.xml", "classpath:/applicationContext-data.xml"})
@TransactionConfiguration(transactionManager="transactionManager")
public abstract class AbstractBaseTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    static {
        System.setProperty("env", "dev");
        System.setProperty("sys:catalina.base", "C:\\gym-home");
    }
    
    @Before
    public void setUp() throws Exception {
        //executeSqlScript("truncate.sql", true);
        //executeSqlScript("dictionary.sql", true);
        //executeSqlScript("data.sql", true);
    }

}
