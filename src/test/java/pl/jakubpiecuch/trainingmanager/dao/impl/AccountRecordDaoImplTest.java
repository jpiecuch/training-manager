package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.BaseUnitDaoTestCase;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;
import pl.jakubpiecuch.trainingmanager.service.repository.accountrecord.AccountRecordCriteria;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rico on 2015-06-13.
 */
public class AccountRecordDaoImplTest extends BaseUnitDaoTestCase {

    private static final Long ID = 1l;
    @Autowired
    private RepoDao<AccountRecord, AccountRecordCriteria> accountRecordDao;

    @Before
    public void setUp() {
        addUserToContext();
    }

    @Test
    public void testFindByCriteriaNull() {
        PageResult<AccountRecord> result = accountRecordDao.findByCriteria(null);

        assertEquals(0l, result.getCount());
    }

    @Test
    @Transactional
    public void testFindByCriteriaEmpty() {
        PageResult<AccountRecord> result = accountRecordDao.findByCriteria(new AccountRecordCriteria("en"));

        assertEquals(1l, result.getCount());
        AccountRecord record = result.getResult().get(0);

        assertRecord(record);
    }

    private void assertRecord(AccountRecord record) {
        assertEquals(ID, record.getId());
        assertEquals(1l, record.getAccount().getId().longValue());
        assertEquals(new LocalDate(2014,12,7).toDate(), record.getDate());
        assertEquals(AccountRecord.Type.WEIGHT, record.getType());
        assertEquals("80.0", record.getValue());
    }

}