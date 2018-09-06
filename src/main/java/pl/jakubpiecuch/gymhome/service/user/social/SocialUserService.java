package pl.jakubpiecuch.gymhome.service.user.social;

import org.springframework.social.security.SocialUserDetailsService;
import pl.jakubpiecuch.gymhome.service.user.UserService;

/**
 * Created by Rico on 2014-11-22.
 */
public interface SocialUserService extends SocialUserDetailsService, UserService {
}
