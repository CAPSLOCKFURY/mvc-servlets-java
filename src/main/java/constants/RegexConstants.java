package constants;

import java.util.regex.Pattern;

public class RegexConstants {
    public final static Pattern LOGIN_PATTERN = Pattern.compile("[a-zA-Z_0-9-]+");
    public final static Pattern EMAIL_PATTERN = Pattern.compile("[a-z0-9.]+@[a-z]+(\\.com|\\.net|\\.ukr|\\.ru|\\.ua)");
    public final static Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z_0-9-]+");
    public final static Pattern NAME_PATTERN =  Pattern.compile("[а-яА-Я|a-zA-Z]+");
}
