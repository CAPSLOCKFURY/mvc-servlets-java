package utils;

import exceptions.UnsupportedEncodingRuntimeException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public final class UTF8UrlCoder {

    public static String encode(String s){
        try{
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e){
            throw new UnsupportedEncodingRuntimeException(e.getMessage());
        }
    }

    public static String decode(String s){
        try{
            return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e){
            throw new UnsupportedEncodingRuntimeException(e.getMessage());
        }
    }

    private UTF8UrlCoder() {
        throw new UnsupportedOperationException();
    }
}
