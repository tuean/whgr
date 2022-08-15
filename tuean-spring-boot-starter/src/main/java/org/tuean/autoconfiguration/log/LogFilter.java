package org.tuean.autoconfiguration.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.tuean.autoconfiguration.util.LogUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

public class LogFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        StringBuffer logBuffer = new StringBuffer();
        LinkedList<String> logParams = new LinkedList<String>();

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        SpringRequestWrapper requestWrapper = new SpringRequestWrapper(request);
        SpringResponseWrapper responseWrapper = new SpringResponseWrapper(response);

        String methodName = request.getMethod(), requestUrl = LogUtil.getRequestUrl(request);

        LogUtil.setReqIdInMdc(request);

        boolean needToLog = false; // todo
        if (!needToLog) {
            filterChain.doFilter(request, response);
            return;
        }



    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
