package forms.base.renderers;

import forms.base.annotations.HtmlTextArea;

public class HtmlTextAreaRenderer {
    private final String name;
    private final String cols;
    private final String rows;

    public String render(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<textarea name=\"%s\" rows=\"%s\" cols=\"%s\">", name, rows, cols));
        sb.append("\n</textarea>");
        return sb.toString();
    }

    public static class Builder{
        private String rows;
        private String cols;
        private String name;

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

        public HtmlTextAreaRenderer build(){
            return new HtmlTextAreaRenderer(this);
        }
    }

    private HtmlTextAreaRenderer(Builder builder){
        name = builder.name;
        cols = builder.cols;
        rows = builder.rows;
    }
}
