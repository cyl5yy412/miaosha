package com.lnsoft.test.io;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 下载的基本原理
 * Created By Chr on 2019/2/27/0027.
 */
public class UploadDemo extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        InputStream is = new FileInputStream(new File("045.jpg"));
        resp.setHeader("Content-Disposition", "attachment;filename=5v.jpg");
        OutputStream os = resp.getOutputStream();

        int length;
        byte[] data = new byte[1024 << 3];
        while ((length = is.read(data)) != -1) {
            os.write(data, 0, length);
        }
        is.close();
        os.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
