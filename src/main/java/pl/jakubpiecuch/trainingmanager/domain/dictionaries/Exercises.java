package pl.jakubpiecuch.trainingmanager.domain.dictionaries;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "exercises", schema = "dictionaries")
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Exercises implements Serializable {
    public enum PartyMusclesEnum {
        ABDOMINALS, BACKS, BICEPS_AND_FLEXORS, CHEST, FOREARMS, LEGS_AND_BUTTOCKS, SHOULDERS, TRICEPS_AND_RECTIFIERS
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "movie_url")
    private String movieUrl;
    @Column(name = "description")
    private String description;
    @Column(name = "party_muscles")
    @Enumerated(EnumType.ORDINAL)
    private PartyMusclesEnum partyMuscles;

    public Exercises(Long id) {
        this.id = id;
    }

    public Exercises() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public PartyMusclesEnum getPartyMuscles() {
        return partyMuscles;
    }

    public void setPartyMuscles(PartyMusclesEnum partyMuscles) {
        this.partyMuscles = partyMuscles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exercises)) {
            return false;
        }
        Exercises other = (Exercises) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.jakubpiecuch.trainingmanager.domain.dictionaries.ExerciseTypes[ id=" + id + " ]";
    }
    
}
