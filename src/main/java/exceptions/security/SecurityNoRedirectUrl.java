package exceptions.security;

public class SecurityNoRedirectUrl extends RuntimeException {

    public SecurityNoRedirectUrl() {
    }

    public SecurityNoRedirectUrl(String message) {
        super(message);
    }
}
