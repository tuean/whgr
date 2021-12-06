package org.tuean.parser;

import java.io.InputStream;
import java.util.List;

public interface IParser<T> {

    T parser(InputStream in);

    T parser(List<String> textLines);


}
