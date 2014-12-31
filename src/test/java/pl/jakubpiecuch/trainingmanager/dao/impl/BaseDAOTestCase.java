package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Created by Rico on 2014-12-27.
 */
@ContextConfiguration(locations = {"classpath:/test-datasource-context.xml"})
public abstract class BaseDAOTestCase extends AbstractTransactionalJUnit4SpringContextTests {

}
