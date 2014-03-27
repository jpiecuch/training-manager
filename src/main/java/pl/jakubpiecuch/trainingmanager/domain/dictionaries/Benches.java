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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "benches", schema = "dictionaries")
public class Benches extends Equipment implements Serializable {
    public enum BenchesEnum {
        SIMPLE, OBLIQUE
    }
    private static final long serialVersionUID = 1L;
    @Column(name = "length_of")
    private Double lengthOf;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "length_of_unit")
    private Units lengthOfUnit;
    @Column(name = "height")
    private Double height;
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "height_unit")
    private Units heightUnit;
    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private BenchesEnum type;

    public Benches() {
    }

    public Benches(Long id) {
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Units getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(Units heightUnit) {
        this.heightUnit = heightUnit;
    }

    public BenchesEnum getType() {
        return type;
    }

    public void setType(BenchesEnum type) {
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
        if (!(object instanceof Benches)) {
            return false;
        }
        Benches other = (Benches) object;
        return (super.getId() != null || other.getId() == null) && (super.getId() == null || super.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return "Benches[ id=" + super.getId() + " ]";
    }

}
