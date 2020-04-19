package com.github.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : hangs.zhang
 * @date : 2017/12/22
 * ********************
 * function :
 */
@WebServlet(name = "CookieClear", urlPatterns = "/cookie/clear")
public class CookieClear extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = new Cookie("myCookie", "myCookie");
        // 一个斜杠表示当前路径
        cookie.setPath("/");

        /*
        负数表示关闭浏览器后立马删除
        正数表示多少秒后删除
        0代表立马删除
         */
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
