package com.tuean.mp.netty.serialize;

import java.io.IOException;

public abstract class AbstractSerializer {

    public abstract <T> byte[] serialize(T obj) throws IOException;

    public abstract <T> Object deserialize(byte[] bytes, Class<T> clazz) throws IOException;



}
