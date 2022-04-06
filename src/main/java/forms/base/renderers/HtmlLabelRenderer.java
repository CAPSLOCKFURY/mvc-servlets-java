package forms.base.renderers;

import java.util.Locale;
import java.util.ResourceBundle;

public class HtmlLabelRenderer {
    private final String forElement;
    private final String text;
    private final String localizedText;
    private final String literal;
    private Locale locale = Locale.ROOT;

    public String render(){
        StringBuilder sb = new StringBuilder();
        String text = this.text;
        if(!localizedText.equals("non-localized")){
            ResourceBundle rb = ResourceBundle.getBundle("forms", locale);
            text = rb.getString("label." + localizedText);
        }
        sb.append(String.format("<label for=\"%s\" ", forElement));
        if(!literal.equals("")){
            sb.append(literal);
        }
        sb.append(String.format(">%s</label>", text));
        return sb.toString();
    }

    public static class Builder{
        private String forElement;
        private String text;
        private String localizedText = "non-localized";
        private String literal = "";

        public Builder withForElement(String forElement){
            this.forElement = forElement;
            return this;
        }

        public Builder withText(String text){
            this.text = text;
            return this;
        }

        public Builder withLocalizedText(String localizedText){
            this.localizedText = localizedText;
            return this;
        }

        public Builder withLiteral(String literal){
            this.literal = literal;
            return this;
        }

        public HtmlLabelRenderer build(){
            return new HtmlLabelRenderer(this);
        }
    }

    private HtmlLabelRenderer(Builder builder){
        forElement = builder.forElement;
        text = builder.text;
        localizedText = builder.localizedText;
        literal = builder.literal;
    }

    public void setLocale(Locale locale){
        this.locale = locale;
    }

}
