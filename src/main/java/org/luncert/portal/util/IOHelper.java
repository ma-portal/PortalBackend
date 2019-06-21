package org.luncert.portal.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class IOHelper {

    public static byte[] read(InputStream inputStream) throws IOException {
        BufferedInputStream buffer = null;
        DataInputStream dataIn = null;
        ByteArrayOutputStream bos = null;
        DataOutputStream dos = null;
        byte[] bArray = null;
        try {
            buffer = new BufferedInputStream(inputStream);
            dataIn = new DataInputStream(buffer);
            bos = new ByteArrayOutputStream();
            dos = new DataOutputStream(bos);
            byte[] buf = new byte[1024];
            while (true) {
                int len = dataIn.read(buf);
                if (len < 0)
                    break;
                dos.write(buf, 0, len);
            }
                        bArray = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (dataIn != null) dataIn.close();
            if (buffer != null) buffer.close();
            if (bos != null) bos.close();
            if (dos != null) dos.close();
        }
        return bArray;
    }

     /**
     * 写数据到HttpServletResponse
     * @param contentType 数据类型，将设置到response header中
     * @param content 数据
     * @param response HttpServletResponse
     * @param cors 是否允许跨源访问
     */
    public static void writeResponse(String contentType, byte[] data, HttpServletResponse response, boolean cors) throws IOException {
        // response.reset();
        // cors
        if (cors) {
            response.setHeader("Access-Control-Allow-Origin", "*");  
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");  
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type");  
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Max-Age", "3600");  
        }
        // content
        if (contentType != null) {
            response.setContentType(contentType);
        }
        response.setContentLength(data.length);
        // output
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

}