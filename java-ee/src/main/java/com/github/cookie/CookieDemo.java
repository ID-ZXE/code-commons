package com.github.cookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author : plum-wine
 * @date : 2017/12/22
 * ********************
 * function :
 */
@WebServlet(name = "CookieDemo", urlPatterns = "/cookie/demo")
public class CookieDemo extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if ("lastAccessTime".equals(cookies[i].getName())) {
                long time = Long.parseLong(cookies[i].getValue());
                Date date = new Date(time);
                DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
                String date_str = df.format(date);
                writer.write("您的最后访问时间是：" + date_str);
            }
        }
        writer.print("<a href= '" + request.getContextPath() + "/cookie/clear'>clear</a>");
        String value = System.currentTimeMillis() + "";
        Cookie cookie = new Cookie("lastAccessTime", value);
        /**
         * 在设置之后，当前应用都能访问该cookie
         */
        cookie.setPath(request.getContextPath());
        /**
         * 更加简洁
         */
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
