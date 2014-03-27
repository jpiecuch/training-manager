package pl.jakubpiecuch.trainingmanager.service.dictionary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.Bars;
import pl.jakubpiecuch.trainingmanager.domain.Benches;
import pl.jakubpiecuch.trainingmanager.domain.Dumbbells;
import pl.jakubpiecuch.trainingmanager.domain.Loads;
import pl.jakubpiecuch.trainingmanager.domain.Necks;
import pl.jakubpiecuch.trainingmanager.domain.Press;
import pl.jakubpiecuch.trainingmanager.domain.Stands;

public class EquipmentSet implements Serializable {
    
    private List<Benches> benches = new ArrayList<Benches>();
    private List<Dumbbells> dumbbells = new ArrayList<Dumbbells>();
    private List<Loads> loads = new ArrayList<Loads>();
    private List<Necks> necks = new ArrayList<Necks>();
    private List<Stands> stands = new ArrayList<Stands>();
    private List<Bars> bars = new ArrayList<Bars>();
    private List<Press> press = new ArrayList<Press>();

    public List<Benches> getBenches() {
        return benches;
    }

    public void setBenches(List<Benches> benches) {
        this.benches = benches;
    }

    public List<Dumbbells> getDumbbells() {
        return dumbbells;
    }

    public void setDumbbells(List<Dumbbells> dumbbells) {
        this.dumbbells = dumbbells;
    }

    public List<Loads> getLoads() {
        return loads;
    }

    public void setLoads(List<Loads> loads) {
        this.loads = loads;
    }

    public List<Necks> getNecks() {
        return necks;
    }

    public void setNecks(List<Necks> necks) {
        this.necks = necks;
    }

    public List<Stands> getStands() {
        return stands;
    }

    public void setStands(List<Stands> standList) {
        this.stands = standList;
    }

    public List<Bars> getBars() {
        return bars;
    }

    public void setBars(List<Bars> barList) {
        this.bars = barList;
    }

    public List<Press> getPress() {
        return press;
    }

    public void setPress(List<Press> press) {
        this.press = press;
    }
}
