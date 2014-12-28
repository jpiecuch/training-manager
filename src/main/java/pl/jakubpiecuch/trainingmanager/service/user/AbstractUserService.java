package pl.jakubpiecuch.trainingmanager.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.common.MapperService;
import pl.jakubpiecuch.trainingmanager.dao.AccountDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.mail.EmailService;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

/**
 * Created by Rico on 2014-11-22.
 */
@Service
public abstract class AbstractUserService implements UserService {

    protected EmailService emailService;
    protected AccountDao accountDao;
    protected MapperService mapperService;

    public abstract boolean isValidCredentials(Account entity, UserDetails user) throws Exception;

    @Override
    public void signIn(UserDetails user) throws Exception {
        SecurityUser securityUser = (SecurityUser) user;
        String username = securityUser.getSocial() != null ? String.format("%s:%s", securityUser.getSocial().getProviderId(), securityUser.getUsername()) : securityUser.getUsername();
        Account entity = accountDao.findByUniques(null, username, null);
        if (isValidCredentials(entity, user)) {
            WebUtil.authenticate(new SecurityUser(entity.getId(), entity.getName(), entity.getPassword(), null, null));
        }
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Autowired
    public void setMapperService(MapperService mapperService) {
        this.mapperService = mapperService;
    }
}
