package pl.jakubpiecuch.gymhome.service.user.workout;

import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.domain.UserWorkout;
import pl.jakubpiecuch.gymhome.domain.Workout;
import pl.jakubpiecuch.gymhome.service.flow.plan.PlanDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.gymhome.service.repository.RepoObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Rico on 2015-01-24.
 */
public class UserWorkoutDto implements RepoObject {

    private Long id;
    private Description.Muscles[] muscles;
    private Date date;
    private PlanDto plan;
    private PhaseDto phase;
    private List<ExecutionDto> executions;
    private Workout.WeekDay weekDay;
    private UserWorkout.State state;
    private String comment;

    public Description.Muscles[] getMuscles() {
        return muscles;
    }

    public void setMuscles(Description.Muscles[] muscles) {
        this.muscles = muscles;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PhaseDto getPhase() {
        return phase;
    }

    public void setPhase(PhaseDto phase) {
        this.phase = phase;
    }

    public PlanDto getPlan() {
        return plan;
    }

    public void setPlan(PlanDto plan) {
        this.plan = plan;
    }

    public List<ExecutionDto> getExecutions() {
        return executions;
    }

    public void setExecutions(List<ExecutionDto> executions) {
        this.executions = executions;
    }

    public Workout.WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Workout.WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public UserWorkout.State getState() {
        return state;
    }

    public void setState(UserWorkout.State state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
