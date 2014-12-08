package pl.jakubpiecuch.trainingmanager.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import pl.jakubpiecuch.trainingmanager.common.MapperService;
import pl.jakubpiecuch.trainingmanager.dao.CalendarsDao;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.mail.EmailService;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

/**
 * Created by Rico on 2014-11-22.
 */
@Service
public abstract class AbstractUserService implements UserService {

    protected CalendarsDao calendarsDao;
    protected EmailService emailService;
    protected UsersDao usersDao;
    protected MapperService mapperService;

    public abstract boolean isValidCredentials(Account entity, UserDetails user, WebRequest request, Response<Authentication> response) throws Exception;

    @Override
    public void signIn(UserDetails user, WebRequest request, Response<Authentication> response) throws Exception {
        Account entity = usersDao.findByUniques(null, user.getUsername(), null);
        if (isValidCredentials(entity, user, request, response)) {
            WebUtil.authenticate(new SecurityUser(response.getEntity().getId(), response.getEntity().getUsername(), response.getEntity().getPassword(), null, null));
        }
    }

    @Autowired
    public void setCalendarsDao(CalendarsDao calendarsDao) {
        this.calendarsDao = calendarsDao;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setUsersDao(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Autowired
    public void setMapperService(MapperService mapperService) {
        this.mapperService = mapperService;
    }
}
