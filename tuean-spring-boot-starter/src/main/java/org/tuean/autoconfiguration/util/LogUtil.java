package org.tuean.autoconfiguration.util;

import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.http.server.PathContainer;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import org.tuean.autoconfiguration.Const;
import org.tuean.autoconfiguration.SpringContextUtil;
import org.tuean.autoconfiguration.log.ReqIdThreadLocal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LogUtil {

    public static String getRequestUrl(HttpServletRequest request) {
        String requestUrl = request.getRequestURI(), contextPath = request.getContextPath();

        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) SpringContextUtil.getBean(RequestMappingHandlerMapping.class);

        String resultIfNotEmpty = getUrlByRequestMappingHandlerMapping(requestMappingHandlerMapping, requestUrl, contextPath);
        if (resultIfNotEmpty != null) return resultIfNotEmpty;

        ServletContext servletContext = request.getSession().getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        if (applicationContext == null) return requestUrl;
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, HandlerMapping.class, true, false);
        if (allRequestMappings == null) return requestUrl;
        for (HandlerMapping mapping : allRequestMappings.values()) {
            if (mapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping handlerMapping = (RequestMappingHandlerMapping) mapping;
                resultIfNotEmpty = getUrlByRequestMappingHandlerMapping(handlerMapping, requestUrl, contextPath);
                if (resultIfNotEmpty!= null) return resultIfNotEmpty;
            }
        }

        return requestUrl;
    }

    public static String getUrlByRequestMappingHandlerMapping(RequestMappingHandlerMapping mappingHandlerMapping,
                                                              String requestUrl, String contextPath) {
        if (mappingHandlerMapping == null) return null;

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mappingHandlerMapping.getHandlerMethods();
        AntPathMatcher matcher = new AntPathMatcher();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods.entrySet()) {
            for (String urlPattern : item.getKey().getPatternsCondition().getPatterns()) {
                urlPattern = contextPath + urlPattern;
                // antPathMatcher
                if (matcher.match(urlPattern, requestUrl)) return urlPattern;

                // pathPatternParser
                PathPattern pattern = PathPatternParser.defaultInstance.parse(urlPattern);
                if (pattern.matches(PathContainer.parsePath(requestUrl))) return urlPattern;

            }
        }
        return null;
    }

    public static void setReqIdInMdc(HttpServletRequest request) {
        if (request == null) return;
        String req = request.getHeader(Const.REQ_ID);
        if (StringUtils.isBlank(req)) {
            req = Util.getRandomString(20);
        }

        MDC.put(Const.THREAD_UUID, req);
        request.setAttribute(Const.REQ_ID, req);
        ReqIdThreadLocal.set(req);
    }


    public static void setReqIdInResp(HttpServletResponse response) {
        if (!response.getHeaderNames().stream().allMatch(n -> n.equals(Const.REQ_ID))) {
            response.addHeader(Const.REQ_ID, ReqIdThreadLocal.get());
        }
    }
}
