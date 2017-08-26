package pl.jakubpiecuch.gymhome.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "description")
public class Description extends RepoCommonEntity {

    public static final String PROPERTY_NAME = "name";
    public static final String LATERAL_NAME = "lateral";
    private String name;
    private String movieUrl;
    private String content;
    private Muscles muscles;
    private Type type;
    private Integer equipment;
    private Level level;
    private Mechanics mechanics;
    private Force force;
    private Lateral lateral;
    private Sets sets;
    public Description(Long id) {
        super(id);
    }
    public Description() {
        super();
    }

    @Column(name = PROPERTY_NAME)
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "movie_url")
    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    @Column(name = "party_muscles")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    public Muscles getMuscles() {
        return muscles;
    }

    public void setMuscles(Muscles muscles) {
        this.muscles = muscles;
    }

    @Column(name = "description")
    public String getContent() {
        return content;
    }

    public void setContent(String description) {
        this.content = description;
    }

    @Column(name = "equipment")
    public Integer getEquipment() {
        return equipment;
    }

    public void setEquipment(Integer equipment) {
        this.equipment = equipment;
    }

    @Column(name = "type")
    @NotNull
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "level")
    @NotNull
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Column(name = "mechanics")
    @NotNull
    public Mechanics getMechanics() {
        return mechanics;
    }

    public void setMechanics(Mechanics mechanics) {
        this.mechanics = mechanics;
    }

    @Column(name = "force")
    @NotNull
    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    @Column(name = "sides")
    @NotNull
    public Lateral getLateral() {
        return lateral;
    }

    public void setLateral(Lateral lateral) {
        this.lateral = lateral;
    }

    @Column(name = "sets")
    @NotNull
    public Sets getSets() {
        return sets;
    }

    public void setSets(Sets sets) {
        this.sets = sets;
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
        Description rhs = (Description) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.name, rhs.name)
                .append(this.movieUrl, rhs.movieUrl)
                .append(this.content, rhs.content)
                .append(this.muscles, rhs.muscles)
                .append(this.type, rhs.type)
                .append(this.equipment, rhs.equipment)
                .append(this.level, rhs.level)
                .append(this.mechanics, rhs.mechanics)
                .append(this.force, rhs.force)
                .append(this.lateral, rhs.lateral)
                .append(this.sets, rhs.sets)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(name)
                .append(movieUrl)
                .append(content)
                .append(muscles)
                .append(type)
                .append(equipment)
                .append(level)
                .append(mechanics)
                .append(force)
                .append(lateral)
                .append(sets)
                .toHashCode();
    }

    public enum Muscles {
        ABS, BICEPS, CALVES, CHEST, FOREARM, GLUTES, HAMSTRINGS, LATS, QUADS, SHOULDERS, TRAPS, TRICEPS
    }

    public enum Type {
        CARDIO, OLYMPIC_WEIGHTLIFTING, PLYOMETRICS, POWERLIFTING, STRENGTH, STRETCHING, STRONGMAN
    }

    public enum Level {
        BEGINNER, INTERMEDIATE, EXPERT
    }

    public enum Mechanics {
        COMPOUND, ISOLATION
    }

    public enum Force {
        PULL, PUSH, STATIC
    }

    public enum Sets {
        REPS, TIME
    }

    public enum Lateral {
        UNILATERAL, BILATERAL
    }
}

