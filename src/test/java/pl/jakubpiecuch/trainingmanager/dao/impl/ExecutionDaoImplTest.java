package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

import java.util.Date;

import static org.junit.Assert.*;

public class ExecutionDaoImplTest extends BaseDAOTestCase {

    public static final Integer[] SETS = new Integer[]{12, 12, 12, 12};
    @Autowired
    private ExecutionDao executionDao;

    @Test
    public void testFindById() throws Exception {
        Execution execution = executionDao.findById(1l);
        assertNotNull(execution);
        for (int i = 0; i < execution.getSets().length; i++) {
            assertEquals(SETS[i], execution.getSets()[i]);
        }
    }

    @Test
    public void testFindByParentId() throws Exception {
        assertFalse(executionDao.findByParentId(1l).isEmpty());
    }

    @Test
    public void testFindByCriteria() {
        SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken(new SecurityUser(1l, "user", "password", null, AuthorityUtils.createAuthorityList("ROLE_ADMIN")), "password", AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
        PageResult result = ((RepoDao) executionDao).findByCriteria(new UserWorkoutCriteria("en").setIdRestriction(1l));
        assertNotNull(result);
        assertEquals(1l, result.getCount());

        result = ((RepoDao) executionDao).findByCriteria(new UserWorkoutCriteria("en").addDateRangeRestriction(new Date(0), null));
        assertNotNull(result);
        assertEquals(1l, result.getCount());
    }
}