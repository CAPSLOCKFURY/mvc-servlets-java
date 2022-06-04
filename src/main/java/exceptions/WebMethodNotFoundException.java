package exceptions;

public class WebMethodNotFoundException extends Exception{
    public WebMethodNotFoundException(String message) {
        super(message);
    }
    public WebMethodNotFoundException(){
        super("Command not found");
    }
}
