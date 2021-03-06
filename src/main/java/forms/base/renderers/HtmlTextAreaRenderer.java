package forms.base.renderers;

public class HtmlTextAreaRenderer {
    private final String id;
    private final String name;
    private final String cols;
    private final String rows;
    private final String literal;

    public String render(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<textarea name=\"%s\" rows=\"%s\" cols=\"%s\" ", name, rows, cols));
        if(!id.equals("")) {
            sb.append(String.format("id=\"%s\" ", id));
        }
        if(!literal.equals("")){
            sb.append(literal);
        }
        sb.append(">");
        sb.append("\n</textarea>");
        return sb.toString();
    }

    public static class Builder{
        private String rows;
        private String cols;
        private String name;
        private String id = "";
        private String literal = "";

        public Builder withRows(String rows){
            this.rows = rows;
            return this;
        }

        public Builder withCols(String cols){
            this.cols = cols;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withLiteral(String literal){
            this.literal = literal;
            return this;
        }

        public HtmlTextAreaRenderer build(){
            return new HtmlTextAreaRenderer(this);
        }
    }

    private HtmlTextAreaRenderer(Builder builder){
        id = builder.id;
        name = builder.name;
        cols = builder.cols;
        rows = builder.rows;
        literal = builder.literal;
    }
}
