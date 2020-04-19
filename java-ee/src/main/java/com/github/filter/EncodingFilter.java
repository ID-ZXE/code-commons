package com.github.filter;

import com.github.wrapper.EncodingWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : plum-wine
 * @date : 2017/12/22
 * ********************
 * function : webFilter 过滤器 拦截所有请求
 */
@WebFilter("/*")
@Slf4j
public class EncodingFilter implements Filter {

    private final static String ENCODING = "UTF-8";

    private final static String GET = "GET";

    private final static String POST = "POST";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("filter");

        if (GET.equals(request.getMethod())) {
            request = new EncodingWrapper(request, ENCODING);
        } else {
            request.setCharacterEncoding("UTF-8");
        }

        response.setContentType("text/html;charset=UTF-8");
        /*
        拦截器放行
         */
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
