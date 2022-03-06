package forms.base.renderers;

import java.util.Map;

public class HtmlSelectRenderer {
    private final String name;
    private final Map<String, String> options;
    private String id = "";

    public String render(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<select name=\"%s\" ", name));
        if(!id.equals("")){
            sb.append(String.format("id=\"%s\" ", id));
        }
        sb.append(">\n");
        for (Map.Entry<String, String> option : options.entrySet()){
            sb.append(String.format("\t<option value=\"%s\">%s</option>\n", option.getKey(), option.getValue()));
        }
        sb.append("</select>");
        return sb.toString();
    }

    public static class Builder{
        private String name;
        private Map<String, String> options;
        private String id = "";

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withOptions(Map<String, String> options){
            this.options = options;
            return this;
        }

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public HtmlSelectRenderer build(){
            return new HtmlSelectRenderer(this);
        }
    }

    private HtmlSelectRenderer(Builder builder){
        id = builder.id;
        name = builder.name;
        options = builder.options;
    }
}
