package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "press")
public class Press extends Equipment {

    private Double strength;
    private Units strengthUnit;
    private Integer handlesNo;

    public Press() {
    }

    public Press(Long id) {
        super(id);
    }

    @Column(name = "strength")
    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "strength_unit")
    public Units getStrengthUnit() {
        return strengthUnit;
    }

    public void setStrengthUnit(Units strengthUnit) {
        this.strengthUnit = strengthUnit;
    }

    @Column(name = "handles_no")
    public Integer getHandlesNo() {
        return handlesNo;
    }

    public void setHandlesNo(Integer handlesNo) {
        this.handlesNo = handlesNo;
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
        if (!(object instanceof Press)) {
            return false;
        }
        Press other = (Press) object;
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
