package pl.jakubpiecuch.trainingmanager.web.authentication;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;


public class SHAPasswordEncoder implements PasswordEncoder, InitializingBean {

    private int strength;
    private ShaPasswordEncoder passwordEncoder;
    
    @Override
    public String encodePassword(String password, Object salt) {
        return passwordEncoder.encodePassword(password, salt);
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        return passwordEncoder.isPasswordValid(rawPass, encPass, salt);
    }
  
    @Override
    public void afterPropertiesSet() throws Exception {
        passwordEncoder = new ShaPasswordEncoder(strength);
    }

    @Required
    public void setStrength(int strength) {
        this.strength = strength;
    }

    
}
