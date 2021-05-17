package com.tuean.whgr.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@Order(1)
public class CorsFilter extends OncePerRequestFilter {

    @Value("${cors.allow.headers}")
    private String allowHeaders;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String methodName = httpServletRequest.getMethod();
        String origin = httpServletRequest.getHeader("origin");
        String referer = httpServletRequest.getHeader("Referer");
        if (!StringUtils.isEmpty(origin)) {
            httpServletResponse.addHeader("Access-Control-Allow-Origin",  origin);
        } else if (!StringUtils.isEmpty(referer)) {
            httpServletResponse.addHeader("Access-Control-Allow-Origin",  referer);
        } else {
            httpServletResponse.addHeader("Access-Control-Allow-Origin",  "*");
        }

        httpServletResponse.addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST, DELETE, PATCH");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", allowHeaders);
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        if ("OPTIONS".equals(methodName)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
