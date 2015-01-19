package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "execution")
public class Execution extends CommonEntity {

    private static final String SET_DELIMITER = ";";

    private String reps;
    private String weight;
    private Exercise exercise;
    private Account account;
    private Boolean confirm;
    private Boolean remind;
    private Date date;
    private String comment;

    public Execution(Long id) {
        super(id);
    }

    public Execution() {
    }

    @Column(name = "reps")
    protected String getReps() {
        return reps;
    }

    protected void setReps(String reps) {
        this.reps = reps;
    }

    @Column(name = "weights")
    protected String getWeight() {
        return weight;
    }

    protected void setWeight(String weight) {
        this.weight = weight;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise")
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "confirm")
    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
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

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Transient
    public Integer[] getSets() {
        return WebUtil.toIntArray(StringUtils.splitByWholeSeparatorPreserveAllTokens(reps, SET_DELIMITER));
    }

    public void setSets(Integer[] sets) {
        this.reps = StringUtils.join(sets, SET_DELIMITER);
    }

    @Transient
    public Double[] getWeights() {
        return WebUtil.toDoubleArray(StringUtils.splitByWholeSeparatorPreserveAllTokens(weight, SET_DELIMITER));
    }

    public void setWeights(Double[] sets) {
        this.weight = StringUtils.join(sets, SET_DELIMITER);
    }
}
