package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dumbbells")
public class Dumbbells extends Equipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "length_of")
    private Double lengthOf;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "length_of_unit")
    private Units lengthOfUnit;
    @Column(name = "weight")
    private Double weight;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weight_unit")
    private Units weightUnit;
    @Column(name = "diameter")
    private Double diameter;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diameter_unit")
    private Units diameterUnit;
    @Column(name = "connected_load")
    private Boolean connectedLoad;

    public Dumbbells() {
    }

    public Dumbbells(Long id) {
        super(id);
    }

    public Double getLengthOf() {
        return lengthOf;
    }

    public void setLengthOf(Double lengthOf) {
        this.lengthOf = lengthOf;
    }

    public Units getLengthOfUnit() {
        return lengthOfUnit;
    }

    public void setLengthOfUnit(Units lengthOfUnit) {
        this.lengthOfUnit = lengthOfUnit;
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

    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    public Units getDiameterUnit() {
        return diameterUnit;
    }

    public void setDiameterUnit(Units diameterUnit) {
        this.diameterUnit = diameterUnit;
    }

    public Boolean getConnectedLoad() {
        return connectedLoad;
    }

    public void setConnectedLoad(Boolean connectedLoad) {
        this.connectedLoad = connectedLoad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Dumbbells)) {
            return false;
        }
        Dumbbells other = (Dumbbells) object;
        return (super.getId() != null || other.getId() == null) && (super.getId() == null || super.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return "Dumbbells[ id=" + super.getId() + " ]";
    }

}
