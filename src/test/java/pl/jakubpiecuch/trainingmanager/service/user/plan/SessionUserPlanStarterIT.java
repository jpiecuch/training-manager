package pl.jakubpiecuch.trainingmanager.service.user.plan;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.BaseIntegrationTestCase;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanCriteria;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;
import pl.jakubpiecuch.trainingmanager.service.repository.Repository;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

import static org.junit.Assert.*;

/**
 * Created by jakub on 26.12.2015.
 */
public class SessionUserPlanStarterIT extends BaseIntegrationTestCase {

    private static final long ACCOUNT_ID = 1l;
    private static final String EN = "en";

    @Autowired
    private UserPlanStarter userPlanStarter;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Repository<Account, AccountCriteria> accountRepository;

    @Autowired
    private Repository<UserWorkoutDto, UserWorkoutCriteria> userWorkoutRepository;

    @Autowired
    private Repository<PlanDto, PlanCriteria> planRepository;

    @Autowired
    private SessionFactory sessionFactory;

    private MockHttpServletRequest request = new MockHttpServletRequest();

    private MockHttpServletResponse response = new MockHttpServletResponse();


    @Test
    @Transactional
    public void testStart() {

        Authentication authentication = new Authentication(accountRepository.unique(ACCOUNT_ID));
        authentication.setProvider(Provider.Type.LOCAL);
        authentication.setPassword("Piecu29");

        authenticationService.signIn(request, response, authentication);

        assertFalse(planRepository.unique(1l).getUsed());

        userPlanStarter.start(1l, 2016, 12);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();


        PageResult<UserWorkoutDto> result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(new DateTime(2016, 3, 1, 8, 0).toDate(), new DateTime(2016, 4, 1, 8, 0).toDate()));

        assertEquals(4l, result.getCount());

        UserWorkoutDto workout1 = result.getResult().get(0);

        assertEquals(new DateTime(2016,3,21,0,0).toDate(), workout1.getDate());
        assertEquals(Workout.WeekDay.MONDAY, workout1.getWeekDay());
        assertEquals(2, workout1.getExecutions().size());

        UserWorkoutDto workout2 = result.getResult().get(1);

        assertEquals(new DateTime(2016,3,23,0,0).toDate(), workout2.getDate());
        assertEquals(Workout.WeekDay.WEDNESDAY, workout2.getWeekDay());
        assertEquals(2, workout2.getExecutions().size());

        UserWorkoutDto workout3 = result.getResult().get(2);

        assertEquals(new DateTime(2016,3,28,0,0).toDate(), workout3.getDate());
        assertEquals(Workout.WeekDay.MONDAY, workout3.getWeekDay());
        assertEquals(2, workout3.getExecutions().size());

        UserWorkoutDto workout4 = result.getResult().get(3);
        assertEquals(new DateTime(2016,3,30,0,0).toDate(), workout4.getDate());
        assertEquals(Workout.WeekDay.WEDNESDAY, workout4.getWeekDay());
        assertEquals(2, workout3.getExecutions().size());

        assertTrue(planRepository.unique(1l).getUsed());

    }

}