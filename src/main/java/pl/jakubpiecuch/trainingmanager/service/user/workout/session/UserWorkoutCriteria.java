package pl.jakubpiecuch.trainingmanager.service.user.workout.session;

import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

import java.util.Date;

/**
 * Created by Rico on 2015-01-18.
 */
public class UserWorkoutCriteria extends Criteria<UserWorkoutCriteria> {

    private Date from;
    private Date to;

    public UserWorkoutCriteria(String lang) {
        super("u", "UserWorkout", lang);
    }

    public UserWorkoutCriteria addDateRangeRestriction(Date from, Date to) {
        this.from = from;
        this.to = to;
        return this;
    }

    @Override
    protected void validateProperty(String property) {

    }

    @Override
    protected void appendRestrictions() {
        restrictions.add(" "+alias+".account.id = :accountId ");
        params.put("accountId", AuthenticatedUserUtil.getUser().getId());

        if (this.id == null) {
            if (this.from != null) {
                restrictions.add(" " + alias + ".date >= :from ");
                params.put("from", this.from);
            }
            if (this.to != null) {
                restrictions.add(" " + alias + ".date <= :to ");
                params.put("to", this.to);
            }
        }
    }
}
