package pl.jakubpiecuch.gymhome.service.encoder.password;

public interface PasswordEncoder {
    String encode(String rawPass, String salt);

    boolean isValid(String encPass, String rawPass, Object salt);
}
