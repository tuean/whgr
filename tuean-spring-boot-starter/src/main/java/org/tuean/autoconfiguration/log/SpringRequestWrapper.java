package org.tuean.autoconfiguration.log;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SpringRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger logger = LoggerFactory.getLogger(SpringRequestWrapper.class);

    private byte[] body;

    public SpringRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            body = IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            logger.error("Failed to read", e);
            body = new byte[0];
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            ByteArrayInputStream bais = new ByteArrayInputStream(body);

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }


}
