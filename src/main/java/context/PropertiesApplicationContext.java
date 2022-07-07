package context;

import dao.factory.SqlDB;

import java.util.ResourceBundle;

public class PropertiesApplicationContext implements ApplicationContext{

    private final SqlDB sqlDB;

    private final String jspPagesPrefix;

    private final String controllersPackage;

    private final String validatorsPackage;

    private final String tasksPackage;

    private final String argumentResolversPackage;

    public PropertiesApplicationContext(String propertiesFileName) {
        ResourceBundle properties = ResourceBundle.getBundle(propertiesFileName);
        sqlDB = SqlDB.valueOf(properties.getString("db.type"));
        jspPagesPrefix = properties.getString("jps.pagesPrefix");
        controllersPackage = properties.getString("componentScan.package.controllers");
        validatorsPackage = properties.getString("componentScan.package.validators.annotations");
        tasksPackage = properties.getString("componentScan.package.tasks");
        argumentResolversPackage = properties.getString("componentScan.package.argumentResolvers");
    }

    @Override
    public SqlDB sqlDb() {
        return sqlDB;
    }

    @Override
    public String jspPagesPrefix() {
        return jspPagesPrefix;
    }

    @Override
    public String controllersPackage() {
        return controllersPackage;
    }

    @Override
    public String validatorsPackage() {
        return validatorsPackage;
    }

    @Override
    public String tasksPackage() {
        return tasksPackage;
    }

    @Override
    public String argumentResolversPackage() {
        return argumentResolversPackage;
    }
}
