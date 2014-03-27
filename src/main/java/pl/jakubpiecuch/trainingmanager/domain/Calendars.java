package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import pl.jakubpiecuch.trainingmanager.domain.Users;

@Entity
@Table(name = "calendars")
public class Calendars extends pl.jakubpiecuch.trainingmanager.domain.Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "name")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "calendar")
    private List<DayExercises> dayExercisesList;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private Users user;

    public Calendars() {
    }

    public Calendars(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DayExercises> getDayExercisesList() {
        return dayExercisesList;
    }

    public void setDayExercisesList(List<DayExercises> dayExercisesList) {
        this.dayExercisesList = dayExercisesList;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
