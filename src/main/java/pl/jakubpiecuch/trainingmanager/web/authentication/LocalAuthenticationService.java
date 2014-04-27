package pl.jakubpiecuch.trainingmanager.web.authentication;

import com.springcryptoutils.core.cipher.symmetric.Base64EncodedCipherer;
import com.springcryptoutils.core.cipher.symmetric.SymmetricEncryptionException;
import java.util.ArrayList;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import pl.jakubpiecuch.trainingmanager.service.mail.EmailService;

@Service
@Transactional
public class LocalAuthenticationService implements AuthenticationService {
    
    protected final static Logger log = LoggerFactory.getLogger(LocalAuthenticationService.class);
    private static final String IV = "AQIDBAUGAQI=";
    private static final String KEY = "Rs3xEA16I52XJpsWwkw4GrB8l6FiVGK/";
    private static final String VAL_SPLITTER = "/";
    private static final String FORMAT = "%s" + VAL_SPLITTER + "%s";
    
    private UsersDao usersDao;
    private CalendarsDao calendarsDao;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private Base64EncodedCipherer encrypter;
    private Base64EncodedCipherer decrypter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersDao.findByUniques(null, username, null);
        if (user == null || Users.Status.ACTIVE != user.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        return new SecurityUser(user.getId(), user.getName(), user.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>(), user.getSalt(), user.getFullName(), user.getCalendar().getId());
    }
    
    @Override
    public ResetStatus resetPassword(String emial) {
        Users user = usersDao.findByUniques(null, null, emial);
        if (user != null) {
            user.setStatus(Users.Status.RESET_PASSWORD);
            usersDao.save(user);
            return ResetStatus.OK;
        }
        return ResetStatus.USER_NOT_EXIST;
    }

    @Override
    public boolean create(Users user, Locale locale) {
        user.setStatus(Users.Status.CREATED);
        user.setSalt(KeyGenerators.string().generateKey());
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getSalt()));
        Calendars calendar = new Calendars();
        calendarsDao.save(calendar);
        user.setCalendar(calendar);
        usersDao.save(user);
        emailService.sendEmail(new Object[] { encrypter.encrypt(KEY, IV, String.format(FORMAT, user.getName(), user.getEmail())), user  }, locale, EmailService.Template.REGISTER, user.getEmail());
        return true;
    }
    
    @Override
    public boolean activate(String value) {
        try {
            String[] decrypt = decrypter.encrypt(KEY, IV, value).split(VAL_SPLITTER);
            Users user = usersDao.findByUniques(null, decrypt[0], decrypt[1]);
            if (user != null && Users.Status.ACTIVE != user.getStatus()) {
                user.setStatus(Users.Status.ACTIVE);
                usersDao.save(user);
                return true;
            }
        } catch (SymmetricEncryptionException e) {
            log.warn("Wrong activate value {}", value);
        }
        return false;
    }

    @Override
    public boolean availability(String field, String value) {
        return usersDao.findByUniques(null, "name".equals(field) ? value : null, "email".equals(field) ? value : null) == null;
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

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    @Qualifier("decrypter")
    public void setDecrypter(Base64EncodedCipherer decrypter) {
        this.decrypter = decrypter;
    }

    @Autowired
    @Qualifier("encrypter")
    public void setEncrypter(Base64EncodedCipherer encrypter) {
        this.encrypter = encrypter;
    }
}
