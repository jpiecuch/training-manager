package pl.jakubpiecuch.trainingmanager.service.encoder.password.sha;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import pl.jakubpiecuch.trainingmanager.service.encoder.password.PasswordEncoder;


public class SHAPasswordEncoder implements PasswordEncoder, InitializingBean {

    private int strength;
    private ShaPasswordEncoder passwordEncoder;

    @Override
    public String encode(String password, String salt) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(salt)) {
            throw new IllegalArgumentException("parameter cannot be empty");
        }
        return passwordEncoder.encodePassword(password, salt);
    }

    @Override
    public boolean isValid(String encPass, String rawPass, Object salt) {
        return passwordEncoder.isPasswordValid(encPass, rawPass, salt);
    }

    @Override
    public void afterPropertiesSet() {
        passwordEncoder = new ShaPasswordEncoder(strength);
    }

    @Required
    public void setStrength(int strength) {
        this.strength = strength;
    }


}
