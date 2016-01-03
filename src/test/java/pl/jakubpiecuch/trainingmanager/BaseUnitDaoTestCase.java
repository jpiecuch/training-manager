package pl.jakubpiecuch.trainingmanager;

import org.junit.runner.RunWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

/**
 * Created by jakub on 19.09.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/unit/hsql-test-datasource-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager")
public abstract class BaseUnitDaoTestCase extends AbstractTransactionalJUnit4SpringContextTests {

    protected void addUserToContext() {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new SecurityUser(1l, "user", "pass", SocialProvider.SocialType.NONE, AuthorityUtils.NO_AUTHORITIES), "pass", AuthorityUtils.NO_AUTHORITIES));
    }

}
