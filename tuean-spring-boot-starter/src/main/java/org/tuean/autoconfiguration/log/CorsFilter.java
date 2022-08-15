package org.tuean.autoconfiguration.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CorsFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    private Set<String> allowHeaders = null;

    public void setAllowedHeaders(Set<String> allowedHeaders) {
        this.allowHeaders = allowedHeaders;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod().trim().toLowerCase();

        if (!response.containsHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS)) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        }

        if (!response.containsHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN)) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader("Origin"));
        }

        if (!response.containsHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS)) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", allowHeaders));
        }

        if (HttpMethod.OPTIONS.name().toLowerCase().equals(request.getMethod())) {
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }



}
