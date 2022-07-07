package context;


public class ContextHolder {

    private final static ContextHolder instance = new ContextHolder();

    private final ApplicationContext applicationContext;

    public static ContextHolder getInstance() {
        return instance;
    }

    public ContextHolder() {
        applicationContext = new PropertiesApplicationContext("application");
    }

    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
