package pl.jakubpiecuch.trainingmanager.web.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class LocalAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
    
    private AuthenticationService localAuthenticationService;
    private PasswordEncoder shaPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        SecurityUser user = (SecurityUser) localAuthenticationService.loadUserByUsername(a.getName());
        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }
        if (!shaPasswordEncoder.encodePassword((String) a.getCredentials(), user.getSalt()).equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> type) {
        return true;
    }

    @Autowired
    public void setLocalAuthenticationService(AuthenticationService localAuthenticationService) {
        this.localAuthenticationService = localAuthenticationService;
    }

    @Autowired
    public void setShaPasswordEncoder(PasswordEncoder shaPasswordEncoder) {
        this.shaPasswordEncoder = shaPasswordEncoder;
    }
}