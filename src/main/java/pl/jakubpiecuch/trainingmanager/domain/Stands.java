package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stands")
public class Stands extends Equipment {

    private Double heightMin;
    private Units heightMinUnit;
    private Double heightMax;
    private Units heightMaxUnit;
    private Integer levels;

    public Stands(Long id) {
        super(id);
    }

    public Stands() {
    }

    @Column(name = "height_min")
    public Double getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(Double heightMin) {
        this.heightMin = heightMin;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "height_min_unit")
    public Units getHeightMinUnit() {
        return heightMinUnit;
    }

    public void setHeightMinUnit(Units heightMinUnit) {
        this.heightMinUnit = heightMinUnit;
    }

    @Column(name = "height_max")
    public Double getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(Double heightMax) {
        this.heightMax = heightMax;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "height_max_unit")
    public Units getHeightMaxUnit() {
        return heightMaxUnit;
    }

    public void setHeightMaxUnit(Units heightMaxUnit) {
        this.heightMaxUnit = heightMaxUnit;
    }

    @Column(name = "levels")
    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stands)) {
            return false;
        }
        Stands other = (Stands) object;
        return (super.getId() != null || other.getId() == null) && (super.getId() == null || super.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return "Stands[ id=" + super.getId() + " ]";
    }

}
