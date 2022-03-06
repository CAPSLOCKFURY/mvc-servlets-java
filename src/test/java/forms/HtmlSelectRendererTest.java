package forms;


import forms.base.renderers.HtmlSelectRenderer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlSelectRendererTest {

    @Test
    public void testHtmlSelectRenderer(){
        Map<String, String> options = new HashMap<>();
        options.put("1", "One");
        options.put("2", "Two");
        HtmlSelectRenderer renderer = new HtmlSelectRenderer.Builder()
                .withName("selectInput").withOptions(options).build();
        assertEquals(
                "<select name=\"selectInput\" >\n" +
                        "\t" + "<option value=\"1\">One</option>\n" +
                        "\t" + "<option value=\"2\">Two</option>\n" +
                        "</select>", renderer.render());
    }

}
