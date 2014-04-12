package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "units")
public class Units extends CommonEntity {

    private String shortName;
    private String name;
    private String description;

    public Units() {
    }
    
    public Units(Long id) {
        super(id);
    }

    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

@Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

@Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "pl.jakubpiecuch.trainingmanager.domain.dictionaries.Units[ id=" + getId() + " ]";
    }
    
}
