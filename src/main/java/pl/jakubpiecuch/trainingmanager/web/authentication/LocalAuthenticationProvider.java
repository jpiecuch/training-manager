package pl.jakubpiecuch.trainingmanager.web.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import pl.jakubpiecuch.trainingmanager.service.encoder.password.PasswordEncoder;
import pl.jakubpiecuch.trainingmanager.service.user.local.LocalUserService;
import pl.jakubpiecuch.trainingmanager.service.user.model.SecurityUser;

@Component
public class LocalAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
    
    private LocalUserService localAuthenticationService;
    private PasswordEncoder shaPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        SecurityUser user = (SecurityUser) localAuthenticationService.loadUserByUsername(a.getName());
        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }
        if (!shaPasswordEncoder.encode((String) a.getCredentials(), user.getSalt()).equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

    @Autowired
    public void setLocalAuthenticationService(LocalUserService localAuthenticationService) {
        this.localAuthenticationService = localAuthenticationService;
    }

    @Autowired
    public void setShaPasswordEncoder(PasswordEncoder shaPasswordEncoder) {
        this.shaPasswordEncoder = shaPasswordEncoder;
    }
}
