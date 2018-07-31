package com.komatsukat.spring.boot.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenpeng23
 * @version 1.0
 * @discription 访问基本拦截器
 * @date 17:33 2018/7/30
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(BaseInterceptor.class);

    /**
     * 前置处理器，请求发送至controllor之前调用
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        log.error("request method: {}", method);
        log.error("request uri: {}", uri);
        log.error("request contextPath: {}", contextPath);
        log.error("preHandle object: {}", handler.toString());

        if (uri.startsWith("/admin") && !uri.startsWith("/admin/login")) {
            log.error("用户未登录，将重定向至登录页面");
            response.sendRedirect( contextPath + "/admin/login");
            return false;
        }

        return true;
    }

    /**
     * 后置处理器，请求发送到controller之后调用
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.error("后置处理器，请求发送到controller之后调用");
    }

    /**
     * 完成请求处理后的调用
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.error("完成请求处理后的调用");
    }
}
