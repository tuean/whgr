package com.tuean.whgr.http;

import org.apache.commons.io.output.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    private PrintWriter printWriter;


    public HttpResponseWrapper(HttpServletResponse response) {
        super(response);
        this.printWriter = new PrintWriter(this.bos);
    }

    public ServletResponse getResponse() {
        return this;
    }

    public ServletOutputStream getOutPutStream() throws IOException {
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            private TeeOutputStream tee;

            {
                this.tee = new TeeOutputStream(HttpResponseWrapper.super.getOutputStream(), HttpResponseWrapper.this.bos);
            }

           public void write(int b) throws IOException {
                this.tee.write(b);
           }
        };
    }

    public PrintWriter getWriter() throws IOException {
        return new TeePrintWriter(super.getWriter(), this.printWriter);
    }

    public byte[] toByteArray() {
        return this.bos.toByteArray();
    }


}
