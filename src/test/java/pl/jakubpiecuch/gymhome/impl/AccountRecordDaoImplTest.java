package pl.jakubpiecuch.gymhome.impl;

import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.gymhome.BaseUnitDaoTestCase;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.dao.core.CoreDao;
import pl.jakubpiecuch.gymhome.domain.AccountRecord;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.service.repository.accountrecord.AccountRecordCriteria;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Rico on 2015-06-13.
 */
public class AccountRecordDaoImplTest extends BaseUnitDaoTestCase<AccountRecord> {

    private static final Long ID = 1l;
    @Autowired
    private RepoDao<AccountRecord, AccountRecordCriteria> accountRecordDao;

    @Test
    public void testFindByCriteriaNull() {
        PageResult<AccountRecord> result = accountRecordDao.findByCriteria(null);

        assertEquals(0l, result.getCount());
    }

    @Test
    @Transactional
    public void testFindByCriteriaEmpty() {
        PageResult<AccountRecord> result = accountRecordDao.findByCriteria(new AccountRecordCriteria("en"));

        assertEquals(2l, result.getCount());
        AccountRecord record = result.getResult().get(0);

        assertRecord(record);

        result = accountRecordDao.findByCriteria(new AccountRecordCriteria("en").setAccountIdRestriction(1l));

        assertEquals(1l, result.getCount());
        record = result.getResult().get(0);
        assertRecord(record);
    }

    private void assertRecord(AccountRecord record) {
        assertEquals(ID, record.getId());
        assertEquals(1l, record.getAccount().getId().longValue());
        assertEquals(new LocalDate(2014,12,7).toDate(), record.getDate());
        assertEquals(AccountRecord.Type.WEIGHT, record.getType());
        assertEquals("80.0", record.getValue());
    }

    @Override
    protected List<String> getNotNullProperties() {
        return Lists.newArrayList("type", "value", "date", "account");
    }

    @Override
    protected CoreDao getDao() {
        return accountRecordDao;
    }

    @Override
    protected AccountRecord getEntity() {
        return new AccountRecord();
    }
}