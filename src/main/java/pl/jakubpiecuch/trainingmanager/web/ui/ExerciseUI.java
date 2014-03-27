package pl.jakubpiecuch.trainingmanager.web.ui;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises.PartyMusclesEnum;


public class ExerciseUI {
    private Long id;
    private PartyMusclesEnum partyMuscles;
    private String partyMusclesMessage;
    private String movieURL;
    private String name;
    private String description;
    

    public static ExerciseUI fromExercise(final Exercises e, MessageSource messageSource, Locale locale) {
        ExerciseUI result = new ExerciseUI();
        result.id = e.getId();
        result.name = e.getName();
        result.partyMuscles = e.getPartyMuscles();
        result.partyMusclesMessage = messageSource.getMessage(String.format("exercise.party.muscles.%s", e.getPartyMuscles()), null, locale);
        result.movieURL = e.getMovieUrl();
        result.description = e.getDescription();
        return result;
    }
    
    public static List<ExerciseUI> fromExerciseList(List<Exercises> list, final MessageSource messageSource, final Locale locale) {
        return Lists.newArrayList(Lists.transform(list, new Function<Exercises, ExerciseUI>() {
            @Override
            public ExerciseUI apply(Exercises e) {
                return fromExercise(e, messageSource, locale);
            }
        }));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovieURL() {
        return movieURL;
    }

    public void setMovieURL(String movieURL) {
        this.movieURL = movieURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PartyMusclesEnum getPartyMuscles() {
        return partyMuscles;
    }

    public void setPartyMuscles(PartyMusclesEnum partyMuscles) {
        this.partyMuscles = partyMuscles;
    }

    public String getPartyMusclesMessage() {
        return partyMusclesMessage;
    }

    public void setPartyMusclesMessage(String partyMusclesMessage) {
        this.partyMusclesMessage = partyMusclesMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
