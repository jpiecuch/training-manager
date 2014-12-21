package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;

@Entity
@Table(name = "equipment")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Equipment<T> extends CommonEntity implements EquipmentDescriptor<T> {
    public interface Type {
        String BAR = "0";
        String BENCH = "1";
        String DUMBBELL = "2";
        String LOAD = "3";
        String NECK = "4";
        String PRESS = "5";
        String STAND = "6";
        String RACK = "7";
    }

    private int type;
    private String data;
    private Double weight;
    private Integer length;
    private Double strength;

    public Equipment() {
    }
    
    public Equipment(Long id) {
        setId(id);
    }

    @Column(name = "type", nullable=false, updatable=false, insertable=false)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "data")
    protected String getData() {
        return data;
    }

    protected void setData(String data) {
        this.data = data;
    }

    @Column(name = "strength")
    public Double getStrength() {
        return strength;
    }

    public void setStrength(Double strength) {
        this.strength = strength;
    }

    @Column(name = "weight")
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "length")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Transient
    public T getConfig() throws Exception {
        return StringUtils.isNotEmpty(this.data) ? (T) WebUtil.fromJson(this.data, getConfigClass()) : null;
    }

    public void setConfig(T config) throws Exception {
        this.data = WebUtil.toJson(config);
    }

    @Entity
    @DiscriminatorValue(Type.BENCH)
    public static class Bench extends Equipment<BenchConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return BenchConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(Type.BAR)
    public static class Bar extends Equipment<BarConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return BarConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(Type.NECK)
    public static class Neck extends Equipment<NeckConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return NeckConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(Type.DUMBBELL)
    public static class Dumbbell extends Equipment<DumbbellConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return DumbbellConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(Type.LOAD)
    public static class Load extends Equipment<LoadConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return LoadConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(Type.PRESS)
    public static class Press extends Equipment<PressConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return PressConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(Type.STAND)
    public static class Stand extends Equipment<StandConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return StandConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(Type.RACK)
    public static class Rack extends Equipment<RackConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return RackConfig.class;
        }
    }
}
