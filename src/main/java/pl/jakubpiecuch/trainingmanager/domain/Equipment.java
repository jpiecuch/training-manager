package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;

@MappedSuperclass()
public class Equipment extends CommonEntity {
    public enum Type {bars, benches, dumbbells, loads, necks, press, stands}

    public Equipment() {
    }
    
    public Equipment(Long id) {
        setId(id);
    }
    
    @JsonIgnore
    @Transient
    public Type getEquipmentType() {
        if (this instanceof Bars) {
            return Type.bars;
        }
        if (this instanceof Benches) {
            return Type.benches;
        }
        if (this instanceof Dumbbells) {
            return Type.dumbbells;
        }
        if (this instanceof Loads) {
            return Type.loads;
        }
        if (this instanceof Necks) {
            return Type.necks;
        }
        if (this instanceof Press) {
            return Type.press;
        }
        if (this instanceof Stands) {
            return Type.stands;
        }
        return null;
    }
}
