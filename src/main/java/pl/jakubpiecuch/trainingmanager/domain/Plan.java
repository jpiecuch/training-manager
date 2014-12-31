package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "plan")
public class Plan extends pl.jakubpiecuch.trainingmanager.domain.CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Goal {muscles, strength}

    private String name;
    private Goal goal;
    private Account creator;

    public Plan() {
    }

    public Plan(Long id) {
        super(id);
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "goal")
    @Enumerated(EnumType.ORDINAL)
    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }
}
