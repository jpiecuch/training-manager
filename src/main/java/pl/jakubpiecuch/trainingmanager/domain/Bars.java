package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "bars")
public class Bars extends Equipment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "length_of")
    private Double lengthOf;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "length_of_unit")
    private Units lengthOfUnit;
    @Column(name = "strength")
    private Double strength;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "strength_unit")
    private Units strengthUnit;
    @Column(name = "handles_no")
    private Integer handlesNo;

    public Bars() {
    }

    public Bars(Long id) {
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

    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    public Units getStrengthUnit() {
        return strengthUnit;
    }

    public void setStrengthUnit(Units strengthUnit) {
        this.strengthUnit = strengthUnit;
    }

    public Integer getHandlesNo() {
        return handlesNo;
    }

    public void setHandlesNo(Integer handlesNo) {
        this.handlesNo = handlesNo;
    }

    @JsonIgnore
    public String getInfo() {
        StringBuilder result = new StringBuilder();
        if (this.strengthUnit != null) {
            result.append("S: ").append(this.strength.toString()).append(" ").append(this.strengthUnit.getShortName()).append(", ");
        }
        if (this.lengthOfUnit != null) {
            result.append("L: ").append(this.lengthOf.toString()).append(" ").append(this.lengthOfUnit.getShortName()).append(", ");
        }
        result.append("Uchwyty: ").append(this.handlesNo);
        return result.toString();
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
        if (!(object instanceof Bars)) {
            return false;
        }
        Bars other = (Bars) object;
        if ((super.getId() == null && other.getId() != null) || (super.getId() != null && !super.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Benches[ id=" + super.getId() + " ]";
    }

}
