package utils;

import java.util.Locale;

public class StringUtils {

    public static String capitalize(String str){
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    private StringUtils(){
        throw new UnsupportedOperationException();
    }
}