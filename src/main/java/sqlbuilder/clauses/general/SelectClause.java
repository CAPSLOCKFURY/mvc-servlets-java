package sqlbuilder.clauses.general;

import sqlbuilder.clauses.base.SqlClause;
import sqlbuilder.model.SqlField;

public class SelectClause implements SqlClause {

    private final SqlField[] sqlFields;

    public SelectClause(SqlField[] sqlFields) {
        this.sqlFields = sqlFields;
    }

    @Override
    public String toSqlString() {
        String[] sqlFieldNames = new String[sqlFields.length];
        for (int i = 0; i < sqlFields.length; i++) {
            sqlFieldNames[i] = sqlFields[i].toString();
        }
        return "select ".concat(String.join(", ", sqlFieldNames));
    }
}
