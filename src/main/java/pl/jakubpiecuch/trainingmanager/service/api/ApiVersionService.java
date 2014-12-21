package pl.jakubpiecuch.trainingmanager.service.api;

import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ApiVersionService {
    enum Version {v1}

    void signIn(Authentication authentication) throws Exception;
    void signOut();
    void signOn(HttpServletRequest request) throws Exception;
    Authentication signed() throws Exception;
    Object language(String lang) throws Exception;
    void locale(HttpServletRequest request, HttpServletResponse response, String locale);
    //old api
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
