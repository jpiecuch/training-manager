package pl.jakubpiecuch.trainingmanager.domain;

import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plan")
public class Plan  extends CommonEntity implements RepoObject {
    private static final long serialVersionUID = 1L;

    public enum Goal {
        MUSCLES, STRENGTH
    }

    private String name;
    private Goal goal;
    private Account creator;
    private Boolean used = false;
    private List<Phase> phases = new ArrayList<Phase>();

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

    @Column(name = "used")
    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Phase> getPhases() {
        return phases;
    }

    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }
}
