package pl.jakubpiecuch.trainingmanager.service.encoder.password.sha;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import pl.jakubpiecuch.trainingmanager.service.encoder.password.PasswordEncoder;


public class SHAPasswordEncoder implements PasswordEncoder, InitializingBean {

    private int strength;
    private ShaPasswordEncoder passwordEncoder;
    
    @Override
    public String encode(String password, Object salt) {
        return passwordEncoder.encodePassword(password, salt);
    }

    @Override
    public boolean isValid(String encPass, String rawPass, Object salt) {
        return passwordEncoder.isPasswordValid(encPass, rawPass, salt);
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
