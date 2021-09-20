package org.tuean.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.model.Resource;

import java.io.File;
import java.util.List;
import java.util.Locale;

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
}
