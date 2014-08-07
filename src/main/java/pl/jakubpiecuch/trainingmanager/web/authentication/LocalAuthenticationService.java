package pl.jakubpiecuch.trainingmanager.web.authentication;

import com.springcryptoutils.core.cipher.symmetric.Base64EncodedCipherer;
import com.springcryptoutils.core.cipher.symmetric.SymmetricEncryptionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.dao.CalendarsDao;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.domain.Calendars;
import pl.jakubpiecuch.trainingmanager.domain.Users;
import pl.jakubpiecuch.trainingmanager.service.mail.EmailService;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

@Service
@Transactional
public class LocalAuthenticationService implements AuthenticationService, SocialUserDetailsService {
    
    protected final static Logger log = LoggerFactory.getLogger(LocalAuthenticationService.class);
    private static final String IV = "AQIDBAUGAQI=";
    private static final String KEY = "Rs3xEA16I52XJpsWwkw4GrB8l6FiVGK/";
    private static final String VAL_SPLITTER = "/";
    private static final String FORMAT = "%s" + VAL_SPLITTER + "%s";
    private static final String OAUTH_PASSWORD = "oauth";
    
    private UsersDao usersDao;
    private CalendarsDao calendarsDao;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private Base64EncodedCipherer encryptService;
    private Base64EncodedCipherer decryptService;
    
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        return (SocialUserDetails) loadUserByUsername(userId);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersDao.findByUniques(null, username, null);
        if (user == null || Users.Status.ACTIVE != user.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        return details(user, null);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username, Connection<?> connection) throws UsernameNotFoundException {
        Users user = usersDao.findByUniques(null, username, null);
        if (user == null || Users.Status.ACTIVE != user.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        return details(user, connection);
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
        emailService.sendEmail(new Object[] { encryptService.encrypt(KEY, IV, String.format(FORMAT, user.getName(), user.getEmail())), user  }, locale, EmailService.Template.REGISTER, user.getEmail());
        return true;
    }

    @Override
    public void socialSignUp(WebRequest request, boolean authenticate) {
        
        Connection<?> connection = ProviderSignInUtils.getConnection(request);
        UserProfile profile = connection.fetchUserProfile();
        
        Users user = new Users();
        user.setEmail(profile.getEmail());
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        user.setName(connection.getKey().getProviderUserId());
        user.setStatus(Users.Status.ACTIVE);
        user.setSalt(KeyGenerators.string().generateKey());
        user.setPassword(OAUTH_PASSWORD);
        
        Calendars calendar = new Calendars();
        calendarsDao.save(calendar);
        
        user.setCalendar(calendar);
        usersDao.save(user);
        ProviderSignInUtils.handlePostSignUp(user.getName(), request);
        if (authenticate) {
            WebUtil.authenticate(details(user, connection));
        }
    }
    
    @Override
    public boolean activate(String value) {
        try {
            String[] decrypt = decryptService.encrypt(KEY, IV, value).split(VAL_SPLITTER);
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
    public List<Social> availableSocials() {
        List<Social> result = new ArrayList<Social>();
        result.add(new Social.Builder().id("facebook").scope("email,read_friendlists,publish_actions").build());
        result.add(new Social.Builder().id("twitter").scope("publish_stream").build());
        result.add(new Social.Builder().id("google").scope("https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile").build());
        return result;
    }
    
    private UserDetails details(Users user, Connection connection) {
        return new SecurityUser(user.getId(), user.getName(), user.getPassword(), true, true, true, true, new ArrayList<GrantedAuthority>(), user.getSalt(), user.getFullName(), user.getCalendar().getId(), connection != null ? Social.Type.valueOf(StringUtils.upperCase(connection.getKey().getProviderId())) : null);
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
    public void setDecryptService(Base64EncodedCipherer decryptService) {
        this.decryptService = decryptService;
    }

    @Autowired
    public void setEncryptService(Base64EncodedCipherer encryptService) {
        this.encryptService = encryptService;
    }
}
