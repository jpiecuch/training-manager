package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.BaseUnitDaoTestCase;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DescriptionDaoImplTest extends BaseUnitDaoTestCase<Description> {

    @Autowired
    private RepoDao<Description, DescriptionCriteria> descriptionDao;

    @Test
    public void testFindByCriteria() {
        PageResult<Description> list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.ABS).addMuscleRestriction(Description.Muscles.BICEPS).setMaxResultsRestriction(10).setFirstResultRestriction(1));
        assertEquals(2, list.getResult().size());
        assertNotNull(list.getResult().get(0));
        assertNotNull(list.getCount());

        list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.ABS).addMuscleRestriction(Description.Muscles.BICEPS));
        assertEquals(3, list.getResult().size());
        assertNotNull(list.getCount());

        list = descriptionDao.findByCriteria(new DescriptionCriteria("en").addMuscleRestriction(Description.Muscles.CALVES).addForceRestriction(Description.Force.PULL));
        assertEquals(0, list.getResult().size());
        assertEquals(0, list.getCount());
    }

    @Test
    public void testGroupBy() {
        Map<String, Long> group = descriptionDao.group(new DescriptionCriteria("en").addGroupBy("force"));
        assertEquals(3, group.size());
        assertEquals(group.get(Description.Force.PULL.toString()).longValue(), 2L);
        assertEquals(group.get(Description.Force.PUSH.toString()).longValue(), 3L);
        assertEquals(group.get(Description.Force.STATIC.toString()).longValue(), 3L);
    }

    @Override
    protected List<String> getNotNullProperties() {
        return Lists.newArrayList("name", "muscles", "type", "level", "mechanics", "force", "sets", "lateral");
    }

    @Override
    protected CoreDao getDao() {
        return descriptionDao;
    }

    @Override
    protected Description getEntity() {
        return new Description();
    }
}