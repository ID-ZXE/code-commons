package com.github.response;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author : plum-wine
 * @date : 2017/12/22
 * ********************
 * function :
 */
@WebServlet(name = "DownloadResponse", urlPatterns = "/response/download")
public class DownloadResponse extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "G:\\file\\archetype\\Alibaba.pdf";
        FileInputStream fis = new FileInputStream(path);
        // 文件名
        String fileName = "Alibaba.pdf";
        // 修改文件名的编码
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);

        ServletOutputStream outputStream = response.getOutputStream();
        int len;
        byte[] buf = new byte[1024];
        while((len = fis.read(buf)) != -1) {
            outputStream.write(buf, 0, len);
        }
    }
}
