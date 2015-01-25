package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;

@Entity
@Table(name = "execution")
public class Execution extends CommonEntity {

    private static final String SET_DELIMITER = ";";

    private String reps;
    private String weight;
    private Exercise exercise;
    private Boolean confirm = false;
    private String comment;
    private UserWorkout workout;

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
    @JoinColumn(name = "workout")
    public UserWorkout getWorkout() {
        return workout;
    }

    public void setWorkout(UserWorkout workout) {
        this.workout = workout;
    }

    @Column(name = "confirm")
    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
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
        return StringUtils.isNotBlank(reps) ? WebUtil.toIntArray(StringUtils.splitByWholeSeparatorPreserveAllTokens(reps, SET_DELIMITER)) : null;
    }

    public void setSets(Integer[] sets) {
        this.reps = StringUtils.join(sets, SET_DELIMITER);
    }

    @Transient
    public Double[] getWeights() {
        return StringUtils.isNotBlank(reps) ? WebUtil.toDoubleArray(StringUtils.splitByWholeSeparatorPreserveAllTokens(weight, SET_DELIMITER)) : null;
    }

    public void setWeights(Double[] sets) {
        this.weight = StringUtils.join(sets, SET_DELIMITER);
    }
}
