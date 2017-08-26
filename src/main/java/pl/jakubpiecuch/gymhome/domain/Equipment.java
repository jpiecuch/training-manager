package pl.jakubpiecuch.gymhome.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.web.util.WebUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Entity
@Table(name = "equipment")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Equipment.Bar.class, name = "BAR"),
        @JsonSubTypes.Type(value = Equipment.Bench.class, name = "BENCH"),
        @JsonSubTypes.Type(value = Equipment.Dumbbell.class, name = "DUMBBELL"),
        @JsonSubTypes.Type(value = Equipment.Load.class, name = "LOAD"),
        @JsonSubTypes.Type(value = Equipment.Barbell.class, name = "BARBELL"),
        @JsonSubTypes.Type(value = Equipment.Press.class, name = "PRESS"),
        @JsonSubTypes.Type(value = Equipment.Rack.class, name = "RACK"),
        @JsonSubTypes.Type(value = Equipment.Stand.class, name = "STAND")
})
public abstract class Equipment<T> extends RepoCommonEntity implements EquipmentDescriptor {
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

    @Column(name = "type", nullable = false, updatable = false, insertable = false)
    @NotNull
    protected int getDiscriminatorType() {
        return type;
    }

    public void setDiscriminatorType(int type) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Equipment rhs = (Equipment) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.type, rhs.type)
                .append(this.data, rhs.data)
                .append(this.weight, rhs.weight)
                .append(this.length, rhs.length)
                .append(this.strength, rhs.strength)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(type)
                .append(data)
                .append(weight)
                .append(length)
                .append(strength)
                .toHashCode();
    }


    public enum Type {
        BAR(Bar.class), BENCH(Bench.class), DUMBBELL(Dumbbell.class), LOAD(Load.class),
        BARBELL(Barbell.class), PRESS(Press.class), STAND(Stand.class), RACK(Rack.class);

        private Class clazz;

        Type(Class clazz) {
            this.clazz = clazz;
        }

        public Class getTypeClass() {
            return this.clazz;
        }
    }

    private static final class DiscriminatorType {
        protected static final String BAR = "0";
        protected static final String BENCH = "1";
        protected static final String DUMBBELL = "2";
        protected static final String LOAD = "3";
        protected static final String BARBELL = "4";
        protected static final String PRESS = "5";
        protected static final String STAND = "6";
        protected static final String RACK = "7";

        private DiscriminatorType() {
        }
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
    @DiscriminatorValue(DiscriminatorType.BARBELL)
    public static class Barbell extends Equipment<BarbellConfig> {

        @Override
        @Transient
        @JsonIgnore
        public Class getConfigClass() {
            return BarbellConfig.class;
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
