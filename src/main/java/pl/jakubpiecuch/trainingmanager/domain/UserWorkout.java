package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Rico on 2015-01-25.
 */
@Entity
@Table(name = "user_workout")
public class UserWorkout extends RepoCommonEntity {

    private String comment;
    private Account account;
    private Boolean remind;
    private Date date;
    private Workout workout;
    private State state;
    private List<Execution> executions;
    public UserWorkout() {
        super();
    }

    public UserWorkout(Long id) {
        super(id);
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "remind")
    public Boolean getRemind() {
        return remind;
    }

    public void setRemind(Boolean remind) {
        this.remind = remind;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout")
    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    @Column(name = "state")
    @Enumerated(EnumType.ORDINAL)
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @OneToMany(mappedBy = "workout")
    public List<Execution> getExecutions() {
        return executions;
    }

    public void setExecutions(List<Execution> executions) {
        this.executions = executions;
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
        UserWorkout rhs = (UserWorkout) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.comment, rhs.comment)
                .append(this.account, rhs.account)
                .append(this.remind, rhs.remind)
                .append(this.date, rhs.date)
                .append(this.workout, rhs.workout)
                .append(this.state, rhs.state)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(comment)
                .append(account)
                .append(remind)
                .append(date)
                .append(workout)
                .append(state)
                .toHashCode();
    }


    public enum State {
        PLANNED, IN_PROGRESS, REJECTED, COMPLETED
    }
}
