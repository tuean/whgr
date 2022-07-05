package com.tuean.whgr.filter;

import com.alibaba.excel.util.StringUtils;
import com.tuean.whgr.helper.ReqHelper;
import com.tuean.whgr.util.StringUtil;
import com.tuean.whgr.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tuean.whgr.config.Const.REQ_ID;


@Component
@Slf4j
@Order(Integer.MIN_VALUE)
public class ReqFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String reqId = request.getHeader(REQ_ID);
        if (StringUtils.isEmpty(reqId)) {
            if (!response.containsHeader(REQ_ID)) {
                reqId = Util.makeReqId();
                response.addHeader(REQ_ID, reqId);
            } else {
                reqId = response.getHeader(REQ_ID);
            }
        }

        ReqHelper.set(reqId);

        filterChain.doFilter(request, response);
    }
}
