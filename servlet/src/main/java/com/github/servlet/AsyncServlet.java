package com.github.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author hangs.zhang
 * @date 2019/3/20
 * *****************
 * function:
 */
@WebServlet(asyncSupported = true, urlPatterns = {"/async"})
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        // 开启异步
        AsyncContext asyncContext = req.startAsync();
        CompletableFuture.runAsync(() -> {
            doSomeThing(asyncContext, asyncContext.getResponse());
        });
        System.out.println("cost : " + (System.currentTimeMillis() - start));
    }

    public static void doSomeThing(AsyncContext asyncContext, ServletResponse response) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            response.getWriter().append("done");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 结束
        asyncContext.complete();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
