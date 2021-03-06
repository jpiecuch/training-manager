package pl.jakubpiecuch.gymhome.service.user.workout.session;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.domain.UserWorkout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public class UserWorkoutCriteria extends Criteria<UserWorkoutCriteria> {
    private static final String[] PROPERTIES = new String[]{"date", "state"};
    private Date from;
    private Date to;
    private List<UserWorkout.State> states = new ArrayList<>();
    private Long accountId;

    public UserWorkoutCriteria(String lang) {
        super("u", "UserWorkout", lang);
        addJoin(LEFT_JOIN_FETCH + alias + ".workout workout");
        addJoin(LEFT_JOIN_FETCH + "workout.phase workout_phase");
        addJoin(LEFT_JOIN_FETCH + "workout_phase.plan workout_phase_plan");
    }

    @Override
    protected String[] getValidFields() {
        return PROPERTIES;
    }

    public UserWorkoutCriteria addDateRangeRestriction(Date from, Date to) {
        this.from = from;
        this.to = to;
        return this;
    }

    public UserWorkoutCriteria addStateRestrictions(UserWorkout.State... states) {
        if (ArrayUtils.isNotEmpty(states)) {
            this.states.addAll(Arrays.asList(states));
        }
        return this;
    }

    public UserWorkoutCriteria setAccountIdRestriction(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    @Override
    protected void appendRestrictions() {

        if (id == null) {
            appendUserRestriction("account.id", accountId);
            appendFromToRestrictions("date", this.from, this.to);
            collection(states, "state", "IN");
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        UserWorkoutCriteria rhs = (UserWorkoutCriteria) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.from, rhs.from)
                .append(this.to, rhs.to)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(from)
                .append(to)
                .toHashCode();
    }
}
