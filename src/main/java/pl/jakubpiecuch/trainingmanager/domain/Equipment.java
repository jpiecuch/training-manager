package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table(name = "equipment")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Equipment<T> extends CommonEntity implements EquipmentDescriptor, RepoObject {
    public enum Type {
        BAR(Bar.class), BENCH(Bench.class), DUMBBELL(Dumbbell.class), LOAD(Load.class),
        NECK(Neck.class), PRESS(Press.class), STAND(Stand.class), RACK(Rack.class);

        private Type(Class clazz){
            this.clazz = clazz;
        }
        private Class clazz;

        public Class getTypeClass() {
            return this.clazz;
        }
    }

    private static final class DiscriminatorType {
        protected static final String BAR = "0";
        protected static final String BENCH = "1";
        protected static final String DUMBBELL = "2";
        protected static final String LOAD = "3";
        protected static final String NECK = "4";
        protected static final String PRESS = "5";
        protected static final String STAND = "6";
        protected static final String RACK = "7";
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
    protected int getDiscriminatorType() {
        return type;
    }

    protected void setDiscriminatorType(int type) {
        this.type = type;
    }

    @Transient
    public Type getType() {
        return Type.values()[type];
    }

    public void setType(Type type) {
        this.type = type.ordinal();
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
    public T getConfig() throws IOException {
        return StringUtils.isNotEmpty(this.data) ? (T) WebUtil.fromJson(this.data, getConfigClass()) : null;
    }

    public void setConfig(T config) {
        this.data = WebUtil.toJson(config);
    }

    @Entity
    @DiscriminatorValue(DiscriminatorType.BENCH)
    public static class Bench extends Equipment<BenchConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return BenchConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(DiscriminatorType.BAR)
    public static class Bar extends Equipment<BarConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return BarConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(DiscriminatorType.NECK)
    public static class Neck extends Equipment<NeckConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return NeckConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(DiscriminatorType.DUMBBELL)
    public static class Dumbbell extends Equipment<DumbbellConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return DumbbellConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(DiscriminatorType.LOAD)
    public static class Load extends Equipment<LoadConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return LoadConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(DiscriminatorType.PRESS)
    public static class Press extends Equipment<PressConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return PressConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(DiscriminatorType.STAND)
    public static class Stand extends Equipment<StandConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return StandConfig.class;
        }
    }

    @Entity
    @DiscriminatorValue(DiscriminatorType.RACK)
    public static class Rack extends Equipment<RackConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return RackConfig.class;
        }
    }
}
