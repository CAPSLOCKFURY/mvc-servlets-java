package exceptions.db;

public class EntityNotFound extends RuntimeException{
    public EntityNotFound(){
        super("404 not found");
    }
}
