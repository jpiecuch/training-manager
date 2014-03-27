package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "loads")
public class Loads extends Equipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "weight")
    private Double weight;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weight_unit")
    private Units weightUnit;
    @Column(name = "hole_diameter")
    private Double holeDiameter;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hole_diameter_unit")
    private Units holeDiameterUnit;

    public Loads() {
    }

    public Loads(Long id) {
        super(id);
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Units getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Units weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Double getHoleDiameter() {
        return holeDiameter;
    }

    public void setHoleDiameter(Double holeDiameter) {
        this.holeDiameter = holeDiameter;
    }

    public Units getHoleDiameterUnit() {
        return holeDiameterUnit;
    }

    public void setHoleDiameterUnit(Units holeDiameterUnit) {
        this.holeDiameterUnit = holeDiameterUnit;
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
        if (!(object instanceof Loads)) {
            return false;
        }
        Loads other = (Loads) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dumbbells[ id=" + super.getId() + " ]";
    }

}
