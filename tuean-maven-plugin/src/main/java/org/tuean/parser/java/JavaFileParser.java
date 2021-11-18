package org.tuean.parser.java;

import org.apache.commons.io.IOUtils;
import org.tuean.entity.define.JavaClass;
import org.tuean.parser.IParser;
import org.tuean.util.Log;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

public class JavaFileParser implements IParser {

    @Override
    public JavaClass parser(InputStream in) {
        try {
            JavaClass result = new JavaClass();
            List<String> lines = IOUtils.readLines(in, Charset.defaultCharset());

            return result;
        } catch (Exception var) {
            Log.getLog().error(var);
            throw new RuntimeException("parse java file error");
        }
    }


}
