package pl.jakubpiecuch.gymhome;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by jakub on 19.09.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/integration/test-service-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager")
public abstract class BaseIntegrationTestCase extends AbstractTransactionalJUnit4SpringContextTests {

}
