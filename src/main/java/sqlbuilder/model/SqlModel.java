package sqlbuilder.model;

public class SqlModel {

    public SqlField get(String fieldName){
        return new SqlField(fieldName);
    }

}
