package com.github.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author : plum-wine
 * @date : 2017/12/22
 * ********************
 * function :
 */
@WebServlet(name = "demo", urlPatterns = "/servlet/demo")
@Slf4j
public class ServletDemo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        获取全局配置信息
         */
        ServletContext application = getServletContext();
        String name = application.getInitParameter("name");
        log.info("name={}", name);

        /*
        当前响应内共享的内存
         */
        application.setAttribute("application", Arrays.asList(1, 2, 3));

        /*
        请求包含
        include()是请求包含
        请求转发
        调用forward()方法后，原先存放在HttpResponse对象中的内容将会自动被清除
        使用postman请求可以发现
        下面这句demo没有打印出来
        都可以共享表单数据
         */
        RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/dispatcher");
        dispatcher.forward(request, response);
        response.getWriter().println("demo");
        log.info("demo");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("requestURL={}", request.getRequestURL());
        log.info("contextPath={}", request.getContextPath());
        log.info("servletPath={}", request.getServletPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/dispatcher");
        dispatcher.include(request, response);
        response.getWriter().println("demo GET");
    }
}
