package exceptions.db;

public class IncorrectDriverPath extends RuntimeException {

    public IncorrectDriverPath(){
        super();
    }

    public IncorrectDriverPath(String path){
        super("Incorrect driver path with path: " + path);
    }
}
