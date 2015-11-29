package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import pl.jakubpiecuch.trainingmanager.BaseIntegrationTestCase;
import pl.jakubpiecuch.trainingmanager.dao.AccountRecordDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rico on 2015-06-13.
 */
public class AccountRecordDaoImplTest extends BaseIntegrationTestCase {

    @Autowired
    private AccountRecordDao accountRecordDao;

    @Test
    public void testFindByCriteriaNull() {
        PageResult<AccountRecord> result = accountRecordDao.findByCriteria(null);

        assertEquals(0l, result.getCount());
    }

}