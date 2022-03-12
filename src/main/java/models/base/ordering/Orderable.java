package models.base.ordering;

public class Orderable {

    private final String orderBy;

    private final OrderDirection direction;

    public Orderable(String orderBy, OrderDirection direction){
        this.orderBy = orderBy;
        this.direction = direction;
    }

    public String orderQuery(String sql){
        return sql.concat(String.format(" order by %s %s", orderBy, direction.toString()));
    }
}
