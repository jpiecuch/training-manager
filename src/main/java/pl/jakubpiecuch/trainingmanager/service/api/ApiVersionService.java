package pl.jakubpiecuch.trainingmanager.service.api;

import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;
import pl.jakubpiecuch.trainingmanager.web.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ApiVersionService {
    enum Version {v1}

    Response signIn(WebRequest request) throws Exception;
    Response signOut();
    Response signOn(HttpServletRequest request) throws Exception;
    Object language(String lang) throws Exception;
    Object exercises(int first, int max, Exercise.PartyMuscles[] partyMuscles);
    void exercise(HttpServletRequest request) throws Exception;
    Object availableSocials();
    void socialPost(SocialProvider.SocialType socialType, long id);
    Object equipments(Integer[] type);
    Object plan(long id);
    Object partyMuscles();
    Object equipmentTypes();
    Object comments(long exerciseId, int first, int max);
    Object comment(HttpServletRequest request) throws Exception;

}
