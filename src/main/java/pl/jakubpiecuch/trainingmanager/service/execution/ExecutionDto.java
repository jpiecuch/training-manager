package pl.jakubpiecuch.trainingmanager.service.execution;

import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import java.util.Date;

/**
 * Created by Rico on 2015-01-18.
 */
public class ExecutionDto implements RepoObject {

    private Long id;
    private Integer[] sets;
    private Double[] weights;
    private Long exerciseId;
    private long accountId;
    private Boolean confirm;
    private Date date;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer[] getSets() {
        return sets;
    }

    public void setSets(Integer[] sets) {
        this.sets = sets;
    }

    public Double[] getWeights() {
        return weights;
    }

    public void setWeights(Double[] weights) {
        this.weights = weights;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
