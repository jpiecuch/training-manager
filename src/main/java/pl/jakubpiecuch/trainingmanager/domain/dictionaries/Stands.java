package pl.jakubpiecuch.trainingmanager.domain.dictionaries;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stands", schema = "dictionaries")
public class Stands extends Equipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "height_min")
    private Double heightMin;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "height_min_unit")
    private Units heightMinUnit;
    @Column(name = "height_max")
    private Double heightMax;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "height_max_unit")
    private Units heightMaxUnit;
    @Column(name = "levels")
    private Integer levels;

    public Stands(Long id) {
        super(id);
    }

    public Stands() {
    }

    public Double getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(Double heightMin) {
        this.heightMin = heightMin;
    }

    public Units getHeightMinUnit() {
        return heightMinUnit;
    }

    public void setHeightMinUnit(Units heightMinUnit) {
        this.heightMinUnit = heightMinUnit;
    }

    public Double getHeightMax() {
        return heightMax;
    }

    public void setHeightMax(Double heightMax) {
        this.heightMax = heightMax;
    }

    public Units getHeightMaxUnit() {
        return heightMaxUnit;
    }

    public void setHeightMaxUnit(Units heightMaxUnit) {
        this.heightMaxUnit = heightMaxUnit;
    }

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
