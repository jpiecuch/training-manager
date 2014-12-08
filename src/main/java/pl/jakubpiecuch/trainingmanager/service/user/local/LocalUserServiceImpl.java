package pl.jakubpiecuch.trainingmanager.service.user.local;

import com.springcryptoutils.core.cipher.symmetric.SymmetricEncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.encoder.password.PasswordEncoder;
import pl.jakubpiecuch.trainingmanager.service.mail.EmailService;
import pl.jakubpiecuch.trainingmanager.service.user.AbstractUserService;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.validator.ValidationUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Locale;

/**
 * Created by Rico on 2014-11-22.
 */
public class LocalUserServiceImpl extends AbstractUserService implements LocalUserService {

    private final static Logger log = LoggerFactory.getLogger(LocalUserServiceImpl.class);
    private static final int EMAIL_CRYPT_POSITION = 1;

    private PasswordEncoder passwordEncoder;
    private CryptService cryptService;


    @Override
    public SecurityUser resolveDetails(InputStream stream) throws Exception {
        Authentication req = mapperService.getObject(stream, Authentication.class);
        return new SecurityUser(null, req.getUsername(), req.getPassword(), null, null);
    }

    @Override
    public boolean isValidCredentials(Account entity, UserDetails user, WebRequest request, Response<Authentication> response) throws Exception {
        if (entity == null) {
            response.addError(new Response.Error.Builder(Response.Error.Type.RUNTIME).value(Response.Error.Code.ACCOUNT_INVALID).build());
        } else if (Account.Status.ACTIVE != entity.getStatus()) {
            response.addError(new Response.Error.Builder(Response.Error.Type.RUNTIME).value(Response.Error.Code.ACCOUNT_INVALID).build());
        }
        if (!passwordEncoder.isValid(entity.getPassword(), user.getPassword(), entity.getSalt())) {
            response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("password").restriction(ValidationUtils.Restriction.INCOMPATIBLE).build());
        }
        if (!response.hasErrors()) {
            response.addEntity(new Authentication(entity));
        }
        return !response.hasErrors();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = usersDao.findByUniques(null, username, null);
        if (account == null || Account.Status.ACTIVE != account.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        return new SecurityUser(account.getId(), account.getName(), account.getPassword(), null, account.getSalt());
    }

    @Override
    public ResetStatus password(String id) {
        Account account = usersDao.findByUniques(null, null, id);
        if (account != null) {
            account.setStatus(Account.Status.RESET_PASSWORD);
            usersDao.save(account);
            return ResetStatus.OK;
        }
        return ResetStatus.USER_NOT_EXIST;
    }

    @Override
    public boolean activate(String id) {
        try {
            String decrypt = cryptService.decrypt(id, EMAIL_CRYPT_POSITION);
            Account account = usersDao.findByUniques(null, null, decrypt);
            if (account != null && Account.Status.ACTIVE != account.getStatus()) {
                account.setStatus(Account.Status.ACTIVE);
                usersDao.save(account);
                return true;
            }
        } catch (SymmetricEncryptionException e) {
            log.warn("Wrong activate value {}", id);
        }
        return false;
    }

    @Override
    public boolean availability(String field, String value) {
        return usersDao.findByUniques(null, "name".equals(field) ? value : null, "email".equals(field) ? value : null) == null;
    }

    @Override
    public void signOn(Registration registration, Response<Registration> response, Locale locale) throws Exception {
        Account account = new Account();
        account.setName(registration.getUsername());
        account.setEmail(registration.getEmail());
        account.setConfig(new Account.Config.Builder().firstName(registration.getFirstName()).lastName(registration.getLastName()).build().toString());
        account.setStatus(Account.Status.CREATED);
        account.setSalt(KeyGenerators.string().generateKey());
        account.setPassword(passwordEncoder.encode(registration.getPassword(), account.getSalt()));
        usersDao.save(account);
        emailService.sendEmail(new Object[] { cryptService.encrypt(account.getName(), account.getEmail()), account}, locale, EmailService.Template.REGISTER, account.getEmail());
        response.addEntity(new Registration(account));
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setCryptService(CryptService cryptService) {
        this.cryptService = cryptService;
    }
}
