package tags;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GetParamJoinerHandler extends TagSupport {
    private String ignoreParams;

    private Boolean insertQuestionMark = true;

    public void setIgnoreParams(String ignoreParams){
        this.ignoreParams = ignoreParams;
    }

    public void setInsertQuestionMark(Boolean insertQuestionMark){
        this.insertQuestionMark = insertQuestionMark;
    }

    private JspWriter out;

    @Override
    public int doStartTag(){
        out = pageContext.getOut();
        List<String> ignoreParamsList = Collections.emptyList();
        if(ignoreParams != null) {
            ignoreParamsList = Arrays.stream(ignoreParams.split(",")).collect(Collectors.toList());
        }
        List<String> finalIgnoreParamsList = ignoreParamsList;
        HttpServletRequest request  = (HttpServletRequest)pageContext.getRequest();
        Map<String, String[]> paramMap = request.getParameterMap();
        String queryParams = paramMap.entrySet().stream()
                .filter(e -> !finalIgnoreParamsList.contains(e.getKey()))
                .flatMap(e -> Arrays.stream(e.getValue()).map(value -> new AbstractMap.SimpleEntry<>(e.getKey(), value)))
                .map(e -> e.getKey().concat("=".concat(e.getValue())))
                .collect(Collectors.joining("&"));
        if(insertQuestionMark){
            write("?".concat(queryParams));
        } else {
            write("&".concat(queryParams));
        }
        return SKIP_BODY;
    }

    private void write(String content){
        try {
            out.println(content);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
