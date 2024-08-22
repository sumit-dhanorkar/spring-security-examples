package com.sumit.sumit.config;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private ByteArrayOutputStream cachedOutputStream;
    private byte[] responseBody;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        cachedOutputStream = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            private OutputStream outputStream = cachedOutputStream;

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }

            @Override
            public void flush() throws IOException {
                outputStream.flush();
            }

            @Override
            public void close() throws IOException {
                outputStream.close();
            }
        };
    }

    @Override
    public void flushBuffer() throws IOException {
        super.flushBuffer();
        responseBody = cachedOutputStream.toByteArray();
    }

//    @Override
//    public PrintWriter getWriter() throws IOException {
//        return new PrintWriter(cachedOutputStream, true);
//    }

    public byte[] getResponseBody() {
        return responseBody;
    }


}
