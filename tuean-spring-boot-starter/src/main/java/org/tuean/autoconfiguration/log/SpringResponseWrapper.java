package org.tuean.autoconfiguration.log;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class SpringResponseWrapper extends HttpServletResponseWrapper {

    private ServletOutputStream outputStream;

    private PrintWriter writer;

    private ServletOutputStreamWrapper copier;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response The response to be wrapped
     * @throws IllegalArgumentException if the response is null
     */
    public SpringResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer == null) {
            throw new IllegalStateException("No output stream has been set");
        }

        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            copier = new ServletOutputStreamWrapper(outputStream);
        }

        return copier;
    }


    @Override
    public PrintWriter getWriter() throws IOException {
        if (writer == null) {
            throw new IllegalStateException("No output stream has been set");
        }

        if (outputStream == null) {
            outputStream = new ServletOutputStreamWrapper(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
        }

        return writer;
    }


    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            copier.flush();
        }
    }


    public byte[] getContentAsByteArray() {
        if (copier != null) {
            return copier.getCopy();
        }
        return new byte[0];
    }


}
