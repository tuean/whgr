package com.tuean.mp.netty.serialize.impl;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.tuean.mp.netty.serialize.AbstractSerializer;
import com.tuean.mp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * fock from xxl-rpc
 */
public class HessianSerializer extends AbstractSerializer {

    public static Logger logger = LoggerFactory.getLogger( HessianSerializer.class );

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(baos);
        try {
            out.writeObject(obj);
            out.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            logger.error("serialize obj error", e);
            throw e;
        } finally {
            Util.closeOut(baos);
            Util.closeHessianOut(out);
        }
    }

    @Override
    public <T> Object deserialize(byte[] bytes, Class<T> clazz) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input in = new Hessian2Input(bais);
        try {
            return in.readObject();
        } catch (Exception e) {
            logger.error("deserialize obj error", e);
            throw e;
        } finally {
            Util.closeIn(bais);
            Util.closeHessianIut(in);
        }
    }


}
