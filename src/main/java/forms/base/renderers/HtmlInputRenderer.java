package forms.base.renderers;

import forms.base.InputType;

import java.util.Locale;
import java.util.ResourceBundle;

public class HtmlInputRenderer {
    private final InputType type;
    private final String name;
    private final String placeholder;
    private final String localizedPlaceholder;

    //TODO put this into Locale class
    private String locale = "ru_RU";

    public String construct(){
        //TODO break this into multiple methods
        StringBuilder sb = new StringBuilder();
        sb.append("<input ");
        sb.append(String.format("type=\"%s\" ", type.toString().toLowerCase()));
        sb.append(String.format("name=\"%s\" ", name));
        if(!placeholder.equals("") && localizedPlaceholder == null){
            sb.append(String.format("placeholder=\"%s\" ", placeholder));
        }
        if(localizedPlaceholder != null){
            sb.append(String.format("placeholder=\"%s\" ",
                    ResourceBundle.getBundle("forms", new Locale(locale))
                            .getString("placeholder." + localizedPlaceholder)));
        }
        sb.append(">");
        return sb.toString();
    }

    public static class Builder{
        private final InputType type;
        private String name;
        private String placeholder = "";
        private String localizedPlaceholder;

        public Builder(InputType type){
            this.type = type;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withPlaceholder(String placeholder){
            this.placeholder = placeholder;
            return this;
        }

        public Builder withLocalizedPlaceholder(String localizedPlaceholder){
            this.localizedPlaceholder = localizedPlaceholder;
            return this;
        }

        public HtmlInputRenderer build(){
            return new HtmlInputRenderer(this);
        }
    }

    private HtmlInputRenderer(Builder builder){
        type = builder.type;
        name = builder.name;
        placeholder = builder.placeholder;
        localizedPlaceholder = builder.localizedPlaceholder;
    }

    public void setLocale(String locale){
        this.locale = locale;
    }
}
