package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.AccountRecordDao;
import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Rico on 2015-06-13.
 */
public class AccountRecordDaoImplTest extends BaseDAOTestCase {

    @Autowired
    private AccountRecordDao accountRecordDao;

    @Test
    public void testFindByAccountIdAndType() {

    }

}