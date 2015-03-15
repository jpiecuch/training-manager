package pl.jakubpiecuch.trainingmanager.domain;

import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import javax.persistence.*;

@Entity
@Table(name = "description")
public class Description extends CommonEntity implements RepoObject {

    public static final String PROPERTY_NAME  = "name";

    public enum Muscles {
        ABDUCTORS, ABS, BICEPS, CALVES, CHEST, FOREARM, GLUTES, HAMSTRINGS, LATS, LOWER_BACK, MIDDLE_BACK, NECK, QUADS, SHOULDERS, TRAPS, TRICEPS
    }
    public enum Type {
        CARDIO, OLYMPIC_WEIGHTLIFTING, PLYOMETRICS, POWERLIFTING, STRENGTH, STRETCHING, STRONMGMAN
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

    private String name;
    private String movieUrl;
    private String description;
    private Muscles muscles;
    private Type type;
    private Integer equipment;
    private Level level;
    private Mechanics mechanics;
    private Force force;

    public Description(Long id) {
        super(id);
    }

    public Description() {
    }

    @Column(name = PROPERTY_NAME)
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
    public Muscles getMuscles() {
        return muscles;
    }

    public void setMuscles(Muscles muscles) {
        this.muscles = muscles;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "equipment")
    public Integer getEquipment() {
        return equipment;
    }

    public void setEquipment(Integer equipment) {
        this.equipment = equipment;
    }

    @Column(name = "type")
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "level")
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Column(name = "mechanics")
    public Mechanics getMechanics() {
        return mechanics;
    }

    public void setMechanics(Mechanics mechanics) {
        this.mechanics = mechanics;
    }

    @Column(name = "force")
    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }
}
