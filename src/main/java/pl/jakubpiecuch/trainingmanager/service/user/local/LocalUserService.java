package pl.jakubpiecuch.trainingmanager.service.user.local;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.jakubpiecuch.trainingmanager.service.user.UserManageService;

/**
 * Created by Rico on 2014-11-22.
 */
public interface LocalUserService extends UserDetailsService, UserManageService {
}
