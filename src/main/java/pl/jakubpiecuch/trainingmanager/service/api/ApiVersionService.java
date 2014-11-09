package pl.jakubpiecuch.trainingmanager.service.api;

import pl.jakubpiecuch.trainingmanager.domain.Equipment;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.web.authentication.AuthenticationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;

public interface ApiVersionService {
    enum Version {v1, v2}

    interface ApiRequest {}

    Object language(String lang) throws Exception;
    Object exercises(int first, int max, Exercise.PartyMuscles[] partyMuscles);
    void exercise(HttpServletRequest request) throws Exception;
    Object availableSocials();
    Object saveDay(HttpServletRequest request) throws Exception;
    Object exerciseProgress(long id);
    Object  dayExercises(Date date);
    void socialPost(AuthenticationService.Social.Type type, long id);
    Object equimpentSet();
    Object equipment(Equipment.Type type);
    Object plan(long id);
    Object partyMuscles();
    Object events(Date start, Date end, Locale locale);
    void moveEvent(HttpServletRequest request) throws Exception;
    void removeEvent(HttpServletRequest request) throws Exception;
    Object createEvent(HttpServletRequest request) throws Exception;
    Object equipmentTypes();
    Object comments(long exerciseId, int first, int max);
    Object comment(HttpServletRequest request) throws Exception;

}
