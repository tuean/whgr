package com.tuean.mp.netty.serialize.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;
import com.tuean.mp.netty.serialize.AbstractSerializer;
import com.tuean.mp.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class KryoSerializer extends AbstractSerializer {

    private static final Logger logger = LoggerFactory.getLogger(KryoSerializer.class);

    static final Pool<Kryo> kryoPool = new Pool<Kryo>(true, false, 8) {
        protected Kryo create () {
            Kryo kryo = new Kryo();
            // Configure the Kryo instance.
            return kryo;
        }
    };

    @Override
    public <T> byte[] serialize(T obj) {
        Kryo kryo = kryoPool.obtain();
        Output out = null;
        try {
            kryo.register(obj.getClass());
            out = new Output();
            kryo.writeClass(out, obj.getClass());
            return out.toBytes();
        } catch (Exception e) {
            logger.error("deserialize obj error", e);
            throw e;
        } finally {
            kryoPool.free(kryo);
            Util.closeKryoOut(out);

        }
    }

    @Override
    public <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        Kryo kryo = kryoPool.obtain();
        Input in = null;
        try {
            kryo.register(clazz);
            in = new Input(bytes);
            return kryo.readObject(in, clazz);
        } catch (Exception e) {
            logger.error("deserialize obj error", e);
            throw e;
        } finally {
            kryoPool.free(kryo);
            Util.closeKryoInput(in);
        }
    }



}
