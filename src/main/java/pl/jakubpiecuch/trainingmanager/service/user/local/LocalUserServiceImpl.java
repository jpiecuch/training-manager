package pl.jakubpiecuch.trainingmanager.service.user.local;

import com.springcryptoutils.core.cipher.symmetric.SymmetricEncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.encoder.password.PasswordEncoder;
import pl.jakubpiecuch.trainingmanager.service.mail.EmailService;
import pl.jakubpiecuch.trainingmanager.service.user.AbstractUserService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import javax.validation.ValidationException;
import java.util.Locale;

/**
 * Created by Rico on 2014-11-22.
 */
public class LocalUserServiceImpl extends AbstractUserService implements LocalUserService {

    private final static Logger log = LoggerFactory.getLogger(LocalUserServiceImpl.class);
    private static final int EMAIL_CRYPT_POSITION = 1;

    private PasswordEncoder passwordEncoder;
    private CryptService cryptService;
    private Validator validator;


    @Override
    public SecurityUser resolveDetails(Authentication authentication)  {
        return new SecurityUser(null, authentication.getUsername(), authentication.getPassword(), null, null);
    }

    @Override
    public boolean isValidCredentials(Account entity, UserDetails user) {
        if (entity == null || Account.Status.ACTIVE != entity.getStatus()) {
            throw new NotFoundException();
        }
        if (!passwordEncoder.isValid(entity.getPassword(), user.getPassword(), entity.getSalt())) {
            throw new ValidationException();
        }
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountDao.findByUniques(null, username, null);
        if (account == null || Account.Status.ACTIVE != account.getStatus()) {
            throw new UsernameNotFoundException("User not exists");
        }
        return new SecurityUser(account.getId(), account.getName(), account.getPassword(), null, account.getSalt());
    }

    @Override
    public ResetStatus password(String id) {
        Account account = accountDao.findByUniques(null, null, id);
        if (account != null) {
            account.setStatus(Account.Status.RESET_PASSWORD);
            accountDao.create(account);
            return ResetStatus.OK;
        }
        return ResetStatus.USER_NOT_EXIST;
    }

    @Override
    public boolean activate(String id) {
        try {
            String decrypt = cryptService.decrypt(id, EMAIL_CRYPT_POSITION);
            Account account = accountDao.findByUniques(null, null, decrypt);
            if (account != null && Account.Status.ACTIVE != account.getStatus()) {
                account.setStatus(Account.Status.ACTIVE);
                accountDao.create(account);
                return true;
            }
        } catch (SymmetricEncryptionException e) {
            log.warn("Wrong activate value {}", id);
        }
        return false;
    }

    @Override
    public boolean availability(String field, String value) {
        return accountDao.findByUniques(null, "name".equals(field) ? value : null, "email".equals(field) ? value : null) == null;
    }

    @Override
    public void signOn(Registration registration, Locale locale) {
        validator.validate(registration, new BeanPropertyBindingResult(registration, "registration"));
        Account account = new Account();
        account.setName(registration.getUsername());
        account.setEmail(registration.getEmail());
        account.setConfig(new Account.Config.Builder().firstName(registration.getFirstName()).lastName(registration.getLastName()).build().toString());
        account.setStatus(Account.Status.CREATED);
        account.setSalt(KeyGenerators.string().generateKey());
        account.setPassword(passwordEncoder.encode(registration.getPassword(), account.getSalt()));
        accountDao.create(account);
        emailService.sendEmail(new Object[] { cryptService.encrypt(account.getName(), account.getEmail()), account}, locale, EmailService.Template.REGISTER, account.getEmail());
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setCryptService(CryptService cryptService) {
        this.cryptService = cryptService;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
