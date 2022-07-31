package org.tuean.autoconfiguration.tdengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class StatsdApiCountFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(StatsdApiCountFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String api = request.getRequestURI();
        CachedStatsdClient.gauge("api.count", 1L, api);
        CachedStatsdClient.timer("api.cost", 100L, api);
    }



}
