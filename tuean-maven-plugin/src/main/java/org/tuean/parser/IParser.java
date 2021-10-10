package org.tuean.parser;

import java.io.InputStream;

public interface IParser<T> {

    void parser(InputStream in);

    T get();

}
