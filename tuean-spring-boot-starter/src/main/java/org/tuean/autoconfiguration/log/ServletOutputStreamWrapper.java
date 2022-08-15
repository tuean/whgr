package org.tuean.autoconfiguration.log;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ServletOutputStreamWrapper extends ServletOutputStream {

    private OutputStream outputStream;

    private ByteArrayOutputStream copy;

    public ServletOutputStreamWrapper(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
        copy.write(b);
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }
}
