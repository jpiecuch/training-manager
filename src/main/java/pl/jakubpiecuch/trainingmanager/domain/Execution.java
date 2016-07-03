package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "execution")
public class Execution extends CommonEntity {

    private Exercise exercise;
    private String comment;
    private UserWorkout workout;
    private UserWorkout.State state;
    private String result;

    public Execution(Long id) {
        super(id);
    }

    public Execution() {
        super();
    }

    @Column(name = "result")
    protected String getResult() {
        return result;
    }

    protected void setResult(String result) {
        this.result = result;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise")
    @NotNull
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout")
    @NotNull
    public UserWorkout getWorkout() {
        return workout;
    }

    public void setWorkout(UserWorkout workout) {
        this.workout = workout;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "state")
    @Enumerated(value = EnumType.ORDINAL)
    @NotNull
    public UserWorkout.State getState() {
        return state;
    }

    public void setState(UserWorkout.State state) {
        this.state = state;
    }

    @Transient
    public List<Result> getResults() {
        return WebUtil.fromJson(result, ResultContainer.class).getResults();
    }

    public void setResults(List<Result> results) {
        result = WebUtil.toJson(new ResultContainer(results));
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
        Execution rhs = (Execution) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.exercise, rhs.exercise)
                .append(this.comment, rhs.comment)
                .append(this.workout, rhs.workout)
                .append(this.state, rhs.state)
                .append(this.result, rhs.result)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(exercise)
                .append(comment)
                .append(workout)
                .append(state)
                .append(result)
                .toHashCode();
    }

    public static class ResultContainer {
        private List<Result> results;

        public ResultContainer() {
            //jackson requires default public constructor
        }

        public ResultContainer(List<Result> results) {
            this.results = results;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }
    }

    public static class Result {

        public static final String LEFT_SIDE_CODE = "LEFT";
        public static final String RIGHT_SIDE_CODE = "RIGHT";

        private String side;
        private List<Double> weights = new ArrayList<Double>();
        private List<Integer> sets = new ArrayList<Integer>();

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public List<Double> getWeights() {
            return weights;
        }

        public void setWeights(List<Double> weights) {
            this.weights = weights;
        }

        public List<Integer> getSets() {
            return sets;
        }

        public void setSets(List<Integer> sets) {
            this.sets = sets;
        }
    }
}
