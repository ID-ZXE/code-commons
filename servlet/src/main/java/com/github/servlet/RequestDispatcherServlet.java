package com.github.servlet;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author : plum-wine
 * @date : 2017/12/22
 * ********************
 * function :
 */
@WebServlet(name = "RequestDispatcherServlet", urlPatterns = "/servlet/dispatcher")
@Slf4j
public class RequestDispatcherServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext application = request.getServletContext();
        List<Integer> list = (List<Integer>) application.getAttribute("application");
        list.forEach(e -> log.info("num={}", e));
        String json = JSON.toJSONString(list);
        PrintWriter printWriter = response.getWriter();
        printWriter.println(json);
        log.info("dispatcher");
        printWriter.println("dispatcher");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("GET");
        response.getWriter().println("dispatcher GET");
    }
}
