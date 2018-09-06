package pl.jakubpiecuch.gymhome.service.user.local;

import com.springcryptoutils.core.cipher.symmetric.SymmetricEncryptionException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.util.UriUtils;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.domain.Role;
import pl.jakubpiecuch.gymhome.service.crypt.CryptService;
import pl.jakubpiecuch.gymhome.service.encoder.password.PasswordEncoder;
import pl.jakubpiecuch.gymhome.service.mail.EmailService;
import pl.jakubpiecuch.gymhome.service.password.PasswordService;
import pl.jakubpiecuch.gymhome.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.gymhome.service.repository.role.RoleCriteria;
import pl.jakubpiecuch.gymhome.service.user.AbstractUserService;
import pl.jakubpiecuch.gymhome.service.user.local.assertion.AccountAssert;
import pl.jakubpiecuch.gymhome.service.user.model.Authentication;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.service.user.model.Registration;
import pl.jakubpiecuch.gymhome.service.user.model.SecurityUser;
import pl.jakubpiecuch.gymhome.service.user.social.SocialProvider;
import pl.jakubpiecuch.gymhome.web.util.WebUtil;

import javax.validation.ValidationException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rico on 2014-11-22.
 */
public class LocalUserServiceImpl extends AbstractUserService implements LocalUserService {
    
    private static final int EMAIL_CRYPT_POSITION = 1;

    private PasswordEncoder passwordEncoder;
    private PasswordService passwordService;
    private CryptService cryptService;
    private Validator validator;
    private String serviceUri;

    @Override
    public SecurityUser resolveDetails(Authentication authentication) {
        Account account = findUser(authentication.getUsername());
        AccountAssert.isTrue(account != null && Account.Status.ACTIVE == account.getStatus());
        List<GrantedAuthority> authorities = CollectionUtils.isNotEmpty(account.getGrantedPermissions()) ? AuthorityUtils.createAuthorityList(account.getGrantedPermissions().toArray(new String[account.getGrantedPermissions().size()])) : AuthorityUtils.NO_AUTHORITIES;
        return new SecurityUser(account.getId(), authentication.getUsername(), passwordEncoder.encode(authentication.getPassword(), account.getSalt()), null, authorities);
    }

    @Override
    public boolean isValidCredentials(Account entity, UserDetails user) {
        AccountAssert.isTrue(entity != null && Account.Status.ACTIVE == entity.getStatus());
        if (!entity.getCredential().equals(user.getPassword())) {
            throw new ValidationException();
        }
        return true;
    }

    @Override
    public void password(String email, Locale locale) {
        Account account = findByEmail(email);
        AccountAssert.isTrue(account != null && Account.Status.ACTIVE == account.getStatus());
        String password = passwordService.generate();
        account.setCredential(passwordEncoder.encode(password, account.getSalt()));
        emailService.sendEmail(new Object[]{password, account, WebUtil.fromJson(account.getConfig(), Account.Config.class)}, locale, EmailService.Template.NEW_PASSWORD, account.getEmail());
        repository.update(account);
    }

    @Override
    public void activate(String code) {
        try {
            String decrypt = cryptService.decrypt(code, EMAIL_CRYPT_POSITION);
            Account account = findByEmail(decrypt);
            AccountAssert.isTrue(account != null && Account.Status.CREATED == account.getStatus());
            account.setStatus(Account.Status.ACTIVE);
            repository.update(account);
        } catch (SymmetricEncryptionException e) {
            throw new IllegalArgumentException("activate.code.error", e);
        }
    }

    @Override
    public void signOn(Registration registration, Locale locale) {
        try {
            validator.validate(registration, new BeanPropertyBindingResult(registration, Registration.NAME));
            Account account = new Account();
            account.setName(registration.getUsername());
            account.setEmail(registration.getEmail());
            account.setConfig(new Account.Config.Builder().firstName(registration.getFirstName()).lastName(registration.getLastName()).build().toString());
            account.setStatus(Account.Status.CREATED);
            account.setSalt(KeyGenerators.string().generateKey());
            account.setCredential(passwordEncoder.encode(registration.getPassword(), account.getSalt()));
            account.setProvider(Provider.Type.LOCAL);
            account.setSocialType(SocialProvider.SocialType.NONE);
            account.getRoles().addAll(roleRepository.page(new RoleCriteria().addNameRestrictions(Role.USER_ROLE)).getResult());
            repository.create(account);
            emailService.sendEmail(new Object[]{UriUtils.encodeQueryParam(cryptService.encrypt(account.getName(), account.getEmail()), "UTF-8"), account, WebUtil.fromJson(account.getConfig(), Account.Config.class), serviceUri}, locale, EmailService.Template.REGISTER, account.getEmail());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Account findByEmail(String email) {
        PageResult<Account> result = repository.page(new AccountCriteria().addEmailRestrictions(email).addProviderRestrictions(Provider.Type.LOCAL));
        if (result.getCount() > 0) {
            return result.getResult().get(0);
        }
        return null;
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

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    public void setServiceUri(String serviceUri) {
        this.serviceUri = serviceUri;
    }
}
