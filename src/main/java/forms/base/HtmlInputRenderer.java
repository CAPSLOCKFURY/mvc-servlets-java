package forms.base;

import utils.LocaleUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class HtmlInputRenderer {
    private final InputType type;
    private final String name;
    private final String placeholder;
    private final String localizedPlaceholder;

    //TODO put this into Locale class
    private String locale = "ru_RU";

    public HtmlInputRenderer(InputType type, String name, String placeholder, String localizedPlaceholder) {
        this.type = type;
        this.name = name;
        this.placeholder = placeholder;
        this.localizedPlaceholder = localizedPlaceholder;
    }

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

    public void setLocale(String locale){
        this.locale = locale;
    }
}
