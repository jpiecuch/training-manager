package pl.jakubpiecuch.gymhome.service.crypt;

public interface CryptService {
    String encrypt(String... args);

    String decrypt(String arg, Integer position);
}
