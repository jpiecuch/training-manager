package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import javax.persistence.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "description")
public class Description extends CommonEntity implements RepoObject {

    private final static String NAME_PERSIST_FORMAT = "%s:%s";
    private final static String NAME_DELIMITER = ";";

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

    @Column(name = "name")
    protected String getName() {
        return name;
    }

    protected void setName(String name) {
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

    @Transient
    public Map<String, String> getNames() {
        Map<String, String> result = null;
        if (StringUtils.isNotEmpty(name)) {
            result = new HashMap<String, String>();
            for (String s : StringUtils.splitByWholeSeparatorPreserveAllTokens(name, NAME_DELIMITER)) {
                String[] tokens = StringUtils.splitPreserveAllTokens(s, ":");
                result.put(tokens[0], tokens[1]);
            }
        }
        return result;
    }

    @JsonDeserialize(using = NamesDeserializer.class)
    protected void setNames(Map<String, String> names) {
        for (Map.Entry<String, String> e : names.entrySet()) {
            addName(e.getKey(), e.getValue());
        }
    }

    public void addName(String lang, String name) {
        this.name = (StringUtils.isNotEmpty(this.name) ? this.name + NAME_DELIMITER : "") + String.format(NAME_PERSIST_FORMAT, lang, name);
    }

    public static class NamesDeserializer extends JsonDeserializer<Map> {

        @Override
        public Map deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            Map result = new HashMap();

            jsonParser.nextToken();
            while (!jsonParser.nextToken().isStructEnd()) {
                result.put(jsonParser.getCurrentName(), jsonParser.getValueAsString());
            }
            return result;
        }
    }

}
