package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bars")
public class Bars extends Equipment {

    private Double lengthOf;
    private Units lengthOfUnit;
    private Double strength;
    private Units strengthUnit;
    private Integer handlesNo;

    public Bars() {
    }

    public Bars(Long id) {
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
    public String toString() {
        return "Bars[ id=" + getId() + " ]";
    }
}
