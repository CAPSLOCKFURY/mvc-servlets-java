package constants;

import java.util.regex.Pattern;

public class RegexConstants {
    public static final Pattern LOGIN_PATTERN = Pattern.compile("[a-zA-Z_0-9-]+");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("[a-z0-9.]+@[a-z]+(\\.com|\\.net|\\.ukr|\\.ru|\\.ua)");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z_0-9-]+");
    public static final Pattern NAME_PATTERN =  Pattern.compile("[а-яА-Я|a-zA-Z]+");
}
