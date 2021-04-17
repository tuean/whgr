package com.tuean.whgr.http;

import org.apache.commons.io.input.TeeInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by zhongxiaotian on 2018/7/26.
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public HttpRequestWrapper(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
    }

    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            private TeeInputStream tee;

            {
                this.tee = new TeeInputStream(HttpRequestWrapper.super.getInputStream(), HttpRequestWrapper.this.bos);
            }

            @Override
            public int read() throws IOException {
                return this.tee.read();
            }
        };
    }

    public byte[] toByteArray() {
        return this.bos.toByteArray();
    }




}
