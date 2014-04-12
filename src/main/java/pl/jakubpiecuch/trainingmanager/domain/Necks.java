package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "necks")
public class Necks extends Equipment {
    public enum NecksEnum {
        STRAIGHT, ANGLED;
    }

    private Double lengthOf;
    private Units lengthOfUnit;
    private Double weight;
    private Units weightUnit;
    private Double diameter;
    private Units diameterUnit;
    private Boolean connectedLoad;
    private NecksEnum type;

    public Necks() {
    }

    public Necks(Long id) {
        super(id);
    }

    @Column(name = "length_of")
    public Double getLengthOf() {
        return lengthOf;
    }

    public void setLengthOf(Double lengthOf) {
        this.lengthOf = lengthOf;
    }

@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "length_of_unit")
    public Units getLengthOfUnit() {
        return lengthOfUnit;
    }

    public void setLengthOfUnit(Units lengthOfUnit) {
        this.lengthOfUnit = lengthOfUnit;
    }

@Column(name = "weight")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "weight_unit")
    public Units getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Units weightUnit) {
        this.weightUnit = weightUnit;
    }

@Column(name = "diameter")
    public Double getDiameter() {
        return diameter;
    }

    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "diameter_unit")
    public Units getDiameterUnit() {
        return diameterUnit;
    }

    public void setDiameterUnit(Units diameterUnit) {
        this.diameterUnit = diameterUnit;
    }

@Column(name = "connected_load")
    public Boolean getConnectedLoad() {
        return connectedLoad;
    }

    public void setConnectedLoad(Boolean connectedLoad) {
        this.connectedLoad = connectedLoad;
    }

@Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    public NecksEnum getType() {
        return type;
    }

    public void setType(NecksEnum type) {
        this.type = type;
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
        if (!(object instanceof Necks)) {
            return false;
        }
        Necks other = (Necks) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Necks[ id=" + super.getId() + " ]";
    }

}
