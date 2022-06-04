package validators;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class RegexCache {

    private static final ConcurrentMap<String, Pattern> regexCache = new ConcurrentHashMap<>();

    public static Pattern getPattern(String pattern){
        return regexCache.computeIfAbsent(pattern, Pattern::compile);
    }

    private RegexCache(){

    }

}
