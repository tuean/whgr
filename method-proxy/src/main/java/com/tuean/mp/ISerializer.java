package com.tuean.mp;

public interface ISerializer {

    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] data, Class<T> clazz);
}
