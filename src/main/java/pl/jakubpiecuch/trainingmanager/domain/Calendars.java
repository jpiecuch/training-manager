package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "calendars")
public class Calendars extends pl.jakubpiecuch.trainingmanager.domain.CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    
    private String name;

    public Calendars() {
    }

    public Calendars(Long id) {
        super(id);
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
