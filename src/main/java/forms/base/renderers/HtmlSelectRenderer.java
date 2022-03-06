package forms.base.renderers;

import java.util.Map;

public class HtmlSelectRenderer {
    private final String name;
    private final Map<String, String> options;

    public String render(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<select name=\"%s\" >\n", name));
        for (Map.Entry option : options.entrySet()){
            sb.append(String.format("\t<option value=\"%s\">%s</option>\n", option.getKey(), option.getValue()));
        }
        sb.append("</select>");
        return sb.toString();
    }

    public static class Builder{
        private String name;
        private Map<String, String> options;

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withOptions(Map<String, String> options){
            this.options = options;
            return this;
        }

        public HtmlSelectRenderer build(){
            return new HtmlSelectRenderer(this);
        }
    }

    private HtmlSelectRenderer(Builder builder){
        name = builder.name;
        options = builder.options;
    }
}
