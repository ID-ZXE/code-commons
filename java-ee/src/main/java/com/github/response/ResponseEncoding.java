package com.github.response;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : plum-wine
 * @date : 2017/12/22
 * ********************
 * function :
 */
@WebServlet(name = "encoding", urlPatterns = "/response/encoding")
public class ResponseEncoding extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, String>> list = new ArrayList<>();
        Map map = new HashMap(2);
        map.put("name", "张航");
        map.put("age", "18岁");
        list.add(map);
        String json = JSON.toJSONString(list);
        response.getWriter().println(json);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<Map<String, String>> list = new ArrayList<>();
        Map map = new HashMap(2);
        map.put("name", "张航");
        map.put("age", "18岁");
        list.add(map);
        String json = JSON.toJSONString(list);
        response.getWriter().println(json);
    }


}
