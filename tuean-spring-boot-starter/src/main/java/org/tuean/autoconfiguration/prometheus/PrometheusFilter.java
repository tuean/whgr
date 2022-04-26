package org.tuean.autoconfiguration.prometheus;

import io.prometheus.client.Histogram;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrometheusFilter implements Filter {

    private final ThreadLocal<Histogram.Timer> timerThreadLocal = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 正在处理的请求量
        PrometheusComponent.getInstance().gauge().labels(request.getRequestURI(), request.getMethod()).inc();

        timerThreadLocal.set(PrometheusComponent.getInstance().histogram()
                .labels(request.getRequestURI(), request.getMethod(), String.valueOf(response.getStatus()))
                .startTimer());

        filterChain.doFilter(request, response);

        String uri = request.getRequestURI();
        String method = request.getMethod();
        int status = response.getStatus();
        // count 请求计数，标签分别为 请求路径，请求方法，response http code
        // 请求应用总量:  sum(demo_rest_req_total)
        // 每秒http请求量: sum(rate(demo_rest_req_total[1m])
        // 请求topk的url:  topk(10, sum(demo_rest_req_total) by (path))
        PrometheusComponent.getInstance().counter().labels(uri, method, String.valueOf(status)).inc();

        // 请求完毕，计数器-1
        PrometheusComponent.getInstance().gauge().labels(uri, method).dec();

        // 直方图统计
        Histogram.Timer timer = timerThreadLocal.get();
        if (timer != null) {
            timer.observeDuration();
            timerThreadLocal.remove();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}