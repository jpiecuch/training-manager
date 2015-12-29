package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.BaseIntegrationTestCase;
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

    @Autowired
    private RepoDao<AccountRecord, AccountRecordCriteria> accountRecordDao;

    @Test
    public void testFindByCriteriaNull() {
        PageResult<AccountRecord> result = accountRecordDao.findByCriteria(null);

        assertEquals(0l, result.getCount());
    }

}