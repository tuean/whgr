package org.tuean.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.model.Resource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static org.tuean.consts.Consts.CONFIG_LINE_SEPARATOR;

public class Util {

    public static void initDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String uppercaseFirst(String key) {
        String k1 = key.substring(0, 1);
        return k1.toUpperCase(Locale.ROOT) + k1.substring(1);
    }

    public static String getMethodName(String key) {
        return "get" + uppercaseFirst(key);
    }

    public static String setMethodName(String key) {
        return "set" + uppercaseFirst(key);
    }

    public static void nextLine(OutputStream out) throws IOException {
        String newLine = System.getProperty(CONFIG_LINE_SEPARATOR);
        out.write(newLine.getBytes(StandardCharsets.UTF_8));
    }
}
