package pl.jakubpiecuch.trainingmanager.web.authentication;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.CalendarsDao;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.domain.Calendars;
import pl.jakubpiecuch.trainingmanager.domain.Users;

@Service
@Transactional
public class LocalAuthenticationService implements AuthenticationService {

    private UsersDao usersDao;
    private CalendarsDao calendarsDao;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersDao.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not exists");
        }
        return new SecurityUser(user.getId(), user.getName(), user.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>(), user.getSalt(), user.getFullName(), user.getCalendar().getId());
    }
    
    @Override
    public ResetStatus resetPassword(String emial) {
        Users user = usersDao.findByEmail(emial);
        if (user != null) {
            user.setStatus(Users.Status.RESET_PASSWORD);
            usersDao.save(user);
            return ResetStatus.OK;
        }
        return ResetStatus.USER_NOT_EXIST;
    }

    @Override
    public boolean create(Users user) {
        user.setStatus(Users.Status.CREATED);
        user.setSalt(KeyGenerators.string().generateKey());
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getSalt()));
        Calendars calendar = new Calendars();
        calendarsDao.save(calendar);
        user.setCalendar(calendar);
        usersDao.save(user);
        return true;
    }

    @Autowired
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Autowired
    public void setCalendarsDao(CalendarsDao calendarsDao) {
        this.calendarsDao = calendarsDao;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
