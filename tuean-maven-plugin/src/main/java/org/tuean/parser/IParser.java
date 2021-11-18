package org.tuean.parser;

import java.io.InputStream;

public interface IParser<T> {

    T parser(InputStream in);


}
