package commands;

import commands.utils.RequestDirection;

public class CommandResult {

    private final String jspPage;
    private final RequestDirection direction;

    public CommandResult(String jspPage, RequestDirection direction){
        this.jspPage = jspPage;
        this.direction = direction;
    }

    public String getJspPage() {
        return jspPage;
    }

    public RequestDirection getDirection() {
        return direction;
    }
}
