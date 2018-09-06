package pl.jakubpiecuch.gymhome.service.social;

import pl.jakubpiecuch.gymhome.service.user.model.SecurityUser;

public interface SocialService {

    void publicMessage(String code);

    boolean createConnection(SecurityUser user);
}
