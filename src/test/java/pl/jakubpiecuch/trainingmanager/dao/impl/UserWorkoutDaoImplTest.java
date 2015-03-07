package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.UserWorkoutDao;

import static org.junit.Assert.*;

public class UserWorkoutDaoImplTest extends BaseDAOTestCase {

    @Autowired
    private UserWorkoutDao userWorkoutDao;

    @Test
    public void testFindByParentId() {
        assertFalse(userWorkoutDao.findByParentId(1l).isEmpty());
    }

}