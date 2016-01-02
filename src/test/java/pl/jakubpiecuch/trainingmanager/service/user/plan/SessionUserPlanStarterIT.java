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
import pl.jakubpiecuch.trainingmanager.domain.*;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanCriteria;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;
import pl.jakubpiecuch.trainingmanager.service.repository.Repository;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.workout.ExecutionDto;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

import java.util.Date;

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

        assertFalse(planRepository.unique(1l).isUsed());

        userPlanStarter.start(1l, 2016, 52);

        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();

        //phase 1 - week 1 - day 1
        Date date = new DateTime(2016, 12, 26, 0, 0).toDate();
        PageResult<UserWorkoutDto> result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        UserWorkoutDto workout = result.getResult().get(0);

        assertPhase1Day1(date, workout);

        //phase 1 - week 1 - day 2
        date = new DateTime(2017, 1, 1, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());
        workout = result.getResult().get(0);

        assertPhase1Day2(date, workout);


        //phase 1 - week 2 - day 1
        date = new DateTime(2017, 1, 2, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase1Day1(date, workout);


        //phase 1 - week 2 - day 2
        date = new DateTime(2017, 1, 8, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase1Day2(date, workout);

        //phase 1 - week 3 - day 1
        date = new DateTime(2017, 1, 9, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase1Day1(date, workout);


        //phase 1 - week 3 - day 2
        date = new DateTime(2017, 1, 15, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase1Day2(date, workout);

        //phase 2 - week 1 - day 1
        date = new DateTime(2017, 1, 18, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase2Day1(date, workout);


        //phase 2 - week 1 - day 2
        date = new DateTime(2017, 1, 20, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase2Day2(date, workout);

        //phase 2 - week 2 - day 1
        date = new DateTime(2017, 1, 25, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase2Day1(date, workout);


        //phase 2 - week 2 - day 2
        date = new DateTime(2017, 1, 27, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase2Day2(date, workout);

        //phase 2 - week 3 - day 1
        date = new DateTime(2017, 2, 1, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase2Day1(date, workout);


        //phase 2 - week 3 - day 2
        date = new DateTime(2017, 2, 3, 0, 0).toDate();
        result = userWorkoutRepository.page(new UserWorkoutCriteria(EN)
                .addDateRangeRestriction(date, date));

        assertEquals(1l, result.getCount());

        workout = result.getResult().get(0);

        assertPhase2Day2(date, workout);

        assertTrue(planRepository.unique(1l).isUsed());


    }

    private void assertPhase2Day2(Date date, UserWorkoutDto workout) {
        assertEquals(date, workout.getDate());
        assertEquals(Workout.WeekDay.FRIDAY, workout.getWeekDay());
        assertEquals(1, workout.getExecutions().size());

        ExecutionDto execution1 = workout.getExecutions().get(0);
        assertEquals(UserWorkout.State.PLANNED, execution1.getState());
        assertEquals(2, execution1.getResults().size());
        assertArrayEquals(new String[] {"12", "12", "12"}, execution1.getExercise().getSets());

        assertEquals(1, workout.getMuscles().length);
        assertEquals(Description.Muscles.NECK, workout.getMuscles()[0]);
    }

    private void assertPhase2Day1(Date date, UserWorkoutDto workout) {
        assertEquals(date, workout.getDate());
        assertEquals(Workout.WeekDay.WEDNESDAY, workout.getWeekDay());
        assertEquals(1, workout.getExecutions().size());

        ExecutionDto execution1 = workout.getExecutions().get(0);
        assertEquals(UserWorkout.State.PLANNED, execution1.getState());
        assertEquals(1, execution1.getResults().size());
        assertArrayEquals(new String[] {"12", "10", "8", "6", "2"}, execution1.getExercise().getSets());

        assertEquals(1, workout.getMuscles().length);
        assertEquals(Description.Muscles.HAMSTRINGS, workout.getMuscles()[0]);
    }

    private void assertPhase1Day2(Date date, UserWorkoutDto workout) {
        assertEquals(date, workout.getDate());
        assertEquals(Workout.WeekDay.SUNDAY, workout.getWeekDay());
        assertEquals(2, workout.getExecutions().size());

        ExecutionDto execution1 = workout.getExecutions().get(0);
        assertEquals(UserWorkout.State.PLANNED, execution1.getState());
        assertEquals(2, execution1.getResults().size());
        assertArrayEquals(new String[] {"12", "12", "12", "12"}, execution1.getExercise().getSets());

        ExecutionDto execution2 = workout.getExecutions().get(1);
        assertEquals(UserWorkout.State.PLANNED, execution1.getState());
        assertEquals(2, execution2.getResults().size());
        assertArrayEquals(new String[] {"12", "10", "8", "6"}, execution2.getExercise().getSets());

        assertEquals(1, workout.getMuscles().length);
        assertEquals(Description.Muscles.BICEPS, workout.getMuscles()[0]);
    }

    private void assertPhase1Day1(Date date, UserWorkoutDto workout) {
        assertEquals(date, workout.getDate());
        assertEquals(Workout.WeekDay.MONDAY, workout.getWeekDay());
        assertEquals(2, workout.getExecutions().size());

        ExecutionDto execution1 = workout.getExecutions().get(0);
        assertEquals(UserWorkout.State.PLANNED, execution1.getState());
        assertEquals(1, execution1.getResults().size());
        assertArrayEquals(new String[] {"12", "10", "8", "6"}, execution1.getExercise().getSets());

        ExecutionDto execution2 = workout.getExecutions().get(1);
        assertEquals(UserWorkout.State.PLANNED, execution1.getState());
        assertEquals(1, execution2.getResults().size());
        assertArrayEquals(new String[] {"12", "10", "8", "6"}, execution2.getExercise().getSets());

        assertEquals(2, workout.getMuscles().length);
        assertEquals(Description.Muscles.CALVES, workout.getMuscles()[0]);
        assertEquals(Description.Muscles.FOREARM, workout.getMuscles()[1]);
    }

}