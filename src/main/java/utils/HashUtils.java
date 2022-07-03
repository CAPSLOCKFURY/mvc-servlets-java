package utils;

import exceptions.security.PasswordHashingException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {

    private static final String ALGORITHM = "MD5";

    public static String hashString(String input){
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] hashedString = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(byte b : hashedString){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException nae){
            nae.printStackTrace();
            throw new PasswordHashingException();
        }
    }

    private HashUtils(){

    }
}
