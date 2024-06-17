package project.nutri.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encrypt {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encoder(String password) {
        return encoder.encode(password);
    }

    //FIXME
    public static Boolean validatePassword(String password, String possiblePassoword) {
        return encoder.matches(password, possiblePassoword);
    }
}
