package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Rico on 2015-01-25.
 */
@Entity
@Table(name = "user_workout")
public class UserWorkout extends CommonEntity {

    public enum State {
        PLANNED, IN_PROGRESS, REJECTED, COMPLETED
    }

    private String comment;
    private Account account;
    private Boolean remind;
    private Date date;
    private Workout workout;
    private State state;

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
}
