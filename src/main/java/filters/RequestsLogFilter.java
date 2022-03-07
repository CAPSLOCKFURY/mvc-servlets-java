package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.UrlUtils;

import java.io.IOException;

public class RequestsLogFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        logger.info("[{}] {} [{}]", httpRequest.getMethod(), UrlUtils.getFullUrl(httpRequest), httpResponse.getStatus());
        chain.doFilter(request, response);
    }
}
