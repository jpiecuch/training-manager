package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.domain.Description;

import static org.junit.Assert.*;

public class DescriptionDaoImplTest extends BaseDAOTestCase {

    @Autowired
    private DescriptionDao descriptionDao;

    @Test
    public void testFindPage() throws Exception {
        descriptionDao.findPage(0,10,new Description.PartyMuscles[] {Description.PartyMuscles.ABDOMINALS});
    }
}