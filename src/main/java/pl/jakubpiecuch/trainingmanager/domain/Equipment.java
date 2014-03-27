package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.MappedSuperclass;
import pl.jakubpiecuch.trainingmanager.domain.Entity;

@MappedSuperclass()
public class Equipment extends Entity {
    public enum Type {bars, benches, dumbbells, loads, necks, press, stands}

    public Equipment() {
    }
    
    public Equipment(Long id) {
        setId(id);
    }
}
