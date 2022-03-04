package exceptions.db;

public class DaoFactoryNotFound extends RuntimeException{
    public DaoFactoryNotFound(){
        super("Dao factory not found");
    }
}
