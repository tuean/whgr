package org.tuean.util;

import org.apache.maven.plugin.logging.SystemStreamLog;

public class Log {

    public static SystemStreamLog log;

    public static SystemStreamLog getLog() {
        if (log == null) log = new SystemStreamLog();
        return log;
    }

}
