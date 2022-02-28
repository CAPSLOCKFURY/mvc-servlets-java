package tags;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class TestTag extends TagSupport {

    public String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            out.println("<h1>msg</h1>".replace("msg", message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

}
