package exceptions.db;

public class ConnectionNotReturned extends RuntimeException {
    public ConnectionNotReturned(){
        super("Could not return connection to connection pool");
    }
}
