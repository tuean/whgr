package org.tuean.parser;

import org.tuean.util.Log;

import java.io.InputStream;

public class JavaFileParser<DaoMethodConfig> implements IParser<DaoMethodConfig>{

    private DaoMethodConfig config;

    @Override
    public void parser(InputStream in) {
        try {

        } catch (Exception var) {
            Log.getLog().error(var);
        }
    }

    @Override
    public DaoMethodConfig get() {
        return config;
    }


}
