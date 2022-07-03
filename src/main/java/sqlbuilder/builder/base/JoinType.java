package sqlbuilder.builder.base;

public enum JoinType {

    LEFT("left"), RIGHT("right"), INNER("inner"), CROSS("cross"),
    LEFT_OUTER("left outer"), RIGHT_OUTER("right outer");

    private final String joinName;

    JoinType(String joinName) {
        this.joinName = joinName;
    }

    public String getJoinName() {
        return joinName;
    }
}
