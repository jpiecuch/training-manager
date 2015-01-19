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
import pl.jakubpiecuch.trainingmanager.service.execution.session.SessionExecutionCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

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

    }

    @Test
    public void testFindByCriteria() {
        SecurityContextHolder.getContext().setAuthentication( new UsernamePasswordAuthenticationToken(new SecurityUser(1l, "user", "password", null, ""), "password", AuthorityUtils.createAuthorityList("ROLE_ADMIN")));
        PageResult result = ((RepoDao) executionDao).findByCriteria(new SessionExecutionCriteria("en").setIdRestriction(1l));
        assertNotNull(result);
        assertEquals(1l, result.getCount());

        result = ((RepoDao) executionDao).findByCriteria(new SessionExecutionCriteria("en").addDateRangeRestriction(new Date(0), null));
        assertNotNull(result);
        assertEquals(1l, result.getCount());
    }
}