package pl.jakubpiecuch.trainingmanager.service.social;

import java.util.Map;

public interface SocialService {

    public interface Params {
        String CODE = "code";
    }
   
    void post(Map<String, String> params);
}
