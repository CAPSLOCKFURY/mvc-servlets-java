package utils;

import java.util.Locale;

public class StringUtils {

    /**
     * Capitalizes given String, making first char in string uppercase
     */
    public static String capitalize(String str){
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }

    public static String getGetterMethod(String str){
        return "get".concat(capitalize(str));
    }

    private StringUtils(){
        throw new UnsupportedOperationException();
    }
}
