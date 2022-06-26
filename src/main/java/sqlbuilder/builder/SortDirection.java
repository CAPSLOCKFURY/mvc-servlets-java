package sqlbuilder.builder;

public enum SortDirection {

    ASC("asc"), DESC("desc");

    private final String sortDirection;

    SortDirection(String sortDirection){
        this.sortDirection = sortDirection;
    }

    public String getSortDirection() {
        return sortDirection;
    }
}
