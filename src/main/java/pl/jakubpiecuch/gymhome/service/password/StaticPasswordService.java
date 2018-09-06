package pl.jakubpiecuch.gymhome.service.password;

import java.util.List;
import java.util.Random;

/**
 * Created by jakub on 19.09.2015.
 */
public class StaticPasswordService implements PasswordService {

    public static final int MINIMUM = 0;
    private List<String> passwords;

    @Override
    public String generate() {
        return passwords.get(position(passwords.size() - 1));
    }

    private int position(int max) {
        Random rn = new Random();
        return rn.nextInt(max - MINIMUM + 1) + MINIMUM;
    }

    public void setPasswords(List<String> passwords) {
        this.passwords = passwords;
    }
}
