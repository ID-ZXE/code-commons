package com.github.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : plum-wine
 * @date : 2017/12/22
 * ********************
 * function :
 */
@WebServlet(name = "ServletConfigDemo", urlPatterns = "/servlet/config", initParams = {
        @WebInitParam(name = "name", value = "zh")
})
@Slf4j
public class ServletConfigDemo extends HttpServlet {

    private String name;

    @Override
    public void init() throws ServletException {
        name = getServletConfig().getInitParameter("name");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("name={}", name);
    }
}
