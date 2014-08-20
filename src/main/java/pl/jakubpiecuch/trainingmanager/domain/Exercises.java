package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "exercises")
public class Exercises extends CommonEntity {

    private final static String NAME_PERSIST_FORMAT = "%s:%s";
    private final static String NAME_DELIMETER = ";";

    public enum PartyMuscles { ABDOMINALS, TRAPS, BICEPS, CHEST, FOREARMS, QUADS, SHOULDERS, TRICEPS, NECK, CALVES, LATS, MIDDLE_BACK, LOWER_BACK, GLUTES, HAMSTRINGS }

    private String name;
    private String movieUrl;
    private String description;
    private PartyMuscles partyMuscles;

    public Exercises(Long id) {
        super(id);
    }

    public Exercises() {
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
    public PartyMuscles getPartyMuscles() {
        return partyMuscles;
    }

    public void setPartyMuscles(PartyMuscles partyMuscles) {
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
        return "pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises[ id=" + getId() + " ]";
    }

    @Transient
    public Map<String, String> getNames() {
        return new HashMap<String, String>() {
            {
                if (StringUtils.isNotEmpty(name)) {
                    for (String s : StringUtils.splitByWholeSeparatorPreserveAllTokens(name, NAME_DELIMETER)) {
                        String[] result = StringUtils.splitPreserveAllTokens(s, ":");
                        put(result[0], result[1]);
                    }
                }
            }
        };
    }
    @JsonDeserialize(using = NamesDeserializer.class)
    protected void setNames(Map<String, String> names) {
        for (Map.Entry<String, String> e : names.entrySet()) {
            addName(e.getKey(), e.getValue());
        }
    }

    public void addName(String lang, String name) {
        this.name = (StringUtils.isNotEmpty(this.name) ? this.name + NAME_DELIMETER : "") + String.format(NAME_PERSIST_FORMAT, lang, name);
    }

    public static class NamesDeserializer extends JsonDeserializer<Map<String, String>> {

        @Override
        public Map<String, String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            Map<String, String> result = new HashMap<String, String>();

            jsonParser.nextToken();
            while (!jsonParser.nextToken().isStructEnd()) {
                result.put(jsonParser.getCurrentName(), jsonParser.getValueAsString());
            }
            return result;
        }
    }

}
