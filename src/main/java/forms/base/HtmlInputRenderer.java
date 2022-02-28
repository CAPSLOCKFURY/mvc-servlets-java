package forms.base;

public class HtmlInputRenderer {
    private final InputType type;
    private final String name;
    private final String placeholder;

    public HtmlInputRenderer(InputType type, String name, String placeholder) {
        this.type = type;
        this.name = name;
        this.placeholder = placeholder;
    }

    public String construct(){
        StringBuilder sb = new StringBuilder();
        sb.append("<input ");
        sb.append(String.format("type=\"%s\" ", type.toString().toLowerCase()));
        sb.append(String.format("name=\"%s\" ", name));
        if(!placeholder.equals("")){
            sb.append(String.format("placeholder=\"%s\" ", placeholder));
        }
        sb.append(">");
        return sb.toString();
    }
}
