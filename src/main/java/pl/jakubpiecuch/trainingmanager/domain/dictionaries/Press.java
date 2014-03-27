package pl.jakubpiecuch.trainingmanager.domain.dictionaries;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "press", schema = "dictionaries")
public class Press extends Equipment {
    private static final long serialVersionUID = 1L;
    
    @Column(name = "strength")
    private Double strength;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "strength_unit")
    private Units strengthUnit;
    @Column(name = "handles_no")
    private Integer handlesNo;

    public Press() {
    }

    public Press(Long id) {
        super(id);
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
