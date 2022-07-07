package context;

import dao.factory.SqlDB;

public interface ApplicationContext {

    SqlDB sqlDb();

    String jspPagesPrefix();

    String controllersPackage();

    String validatorsPackage();

    String tasksPackage();

    String argumentResolversPackage();

}
