package com.github.request;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author : plum-wine
 * @date : 2017/12/22
 * ********************
 * function :
 */
@WebServlet(name = "request", urlPatterns = "/request/demo")
@Slf4j
public class RequestDemo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String pwd = request.getParameter("pwd");
        String sex = request.getParameter("sex");

        log.info("name={}, pwd={}, sex={}", name, pwd, sex);

        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            /*
            values.length 被final修饰 无法更改
            好羞耻啊，今天好奇试了一下才知道
             */
            for(int i = 0, len = values.length; i < len; i++) {
                log.info("value={}", values[i]);
            }
        }
    }
}
