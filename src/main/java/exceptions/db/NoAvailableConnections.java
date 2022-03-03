package exceptions.db;

public class NoAvailableConnections extends RuntimeException {
    public NoAvailableConnections(){
        super("No available connections in connection pool");
    }
}
