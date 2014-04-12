package pl.jakubpiecuch.trainingmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "exercises")
public class Exercises extends CommonEntity {

    public enum PartyMusclesEnum {

        ABDOMINALS, BACKS, BICEPS_AND_FLEXORS, CHEST, FOREARMS, LEGS_AND_BUTTOCKS, SHOULDERS, TRICEPS_AND_RECTIFIERS
    }

    private String name;
    private String movieUrl;
    private String description;
    private PartyMusclesEnum partyMuscles;

    public Exercises(Long id) {
        super(id);
    }

    public Exercises() {
    }

    @Column(name = "name")
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
    public PartyMusclesEnum getPartyMuscles() {
        return partyMuscles;
    }

    public void setPartyMuscles(PartyMusclesEnum partyMuscles) {
        this.partyMuscles = partyMuscles;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "pl.jakubpiecuch.trainingmanager.domain.dictionaries.ExerciseTypes[ id=" + getId() + " ]";
    }

}
