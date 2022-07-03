package exceptions.sqlbuilder;

public class NoVisitorForGivenDB extends RuntimeException {

    public NoVisitorForGivenDB() {
        super();
    }

    public NoVisitorForGivenDB(String message) {
        super(message);
    }
}
