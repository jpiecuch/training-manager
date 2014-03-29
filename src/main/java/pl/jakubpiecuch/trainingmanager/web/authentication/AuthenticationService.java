package pl.jakubpiecuch.trainingmanager.web.authentication;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.domain.Users;

@Service
@Transactional(readOnly = true)
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users daoUser = usersDao.findByName(username);
        if (daoUser == null) {
            throw new UsernameNotFoundException("User not exists");
        }
        return new SecurityUser(daoUser.getId(), daoUser.getName(), daoUser.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>(), daoUser.getSalt(), daoUser.getFullName(), daoUser.getCalendar().getId());
    }
}
