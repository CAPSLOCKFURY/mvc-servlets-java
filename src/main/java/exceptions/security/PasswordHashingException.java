package exceptions.security;

public class PasswordHashingException extends RuntimeException {

    public PasswordHashingException(){
        super("Password hashing failed");
    }

}
