package pl.jakubpiecuch.gymhome.service.user.plan;

/**
 * Created by Rico on 2015-01-22.
 */
public class UserPlan {
    private Long planId;
    private Integer year;
    private Integer week;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }
}
