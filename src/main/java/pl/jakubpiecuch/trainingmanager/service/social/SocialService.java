package pl.jakubpiecuch.trainingmanager.service.social;

import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;

public interface SocialService {
   
    void publicMessage(String code);
    boolean createConnection(SecurityUser user) throws Exception;
}
