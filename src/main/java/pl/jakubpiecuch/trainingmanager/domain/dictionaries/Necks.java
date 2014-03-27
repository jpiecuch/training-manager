package pl.jakubpiecuch.trainingmanager.domain.dictionaries;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "necks", schema = "dictionaries")
public class Necks extends Equipment implements Serializable {
    public enum NecksEnum {
        STRAIGHT, ANGLED;
    }
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
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private NecksEnum type;

    public Necks() {
    }

    public Necks(Long id) {
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
