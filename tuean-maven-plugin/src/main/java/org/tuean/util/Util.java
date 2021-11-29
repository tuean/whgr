package org.tuean.util;

import com.google.inject.internal.util.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.maven.project.MavenProject;
import org.apache.maven.model.Resource;
import org.tuean.entity.define.JavaField;
import org.tuean.entity.define.JavaMethod;
import org.tuean.entity.define.JavaVisible;

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

    public static String dbColumn2JavaField(String column) {
        StringBuffer sb = new StringBuffer();
        boolean _flag = false;
        for (int x = 0; column.length() > x; x++) {
            char t = column.charAt(x);
            boolean flag = t == '_';
            if (flag) {
                _flag = true;
                continue;
            }
            if (_flag && x != 1) {
                sb.append(String.valueOf(column.charAt(x)).toUpperCase());
            } else {
                sb.append(column.charAt(x));
            }
            _flag = false;
        }
        return sb.toString();
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

    public static JavaMethod getMethod(JavaField field) {
        JavaMethod method = new JavaMethod();
        method.setArgClazzs(null);
        method.setArgNames(null);
        method.setJavaVisible(JavaVisible.visiblePublic());
        method.setStatic(false);
        method.setMethodName("get" + uppercaseFirst(field.getFieldName()));
        method.setMethodBody(Lists.newArrayList("return this." + field.getFieldName()));
        return method;
    }

    public static JavaMethod setMethod(JavaField field) {
        JavaMethod method = new JavaMethod();
        method.setArgClazzs(new Class[]{field.getFieldClazz()});
        method.setArgNames(new String[]{field.getFieldName()});
        method.setJavaVisible(JavaVisible.visiblePublic());
        method.setStatic(false);
        method.setMethodName("set" + uppercaseFirst(field.getFieldName()));
        method.setMethodBody(Lists.newArrayList("this." + field.getFieldName() + " = " + field.getFieldName()));
        return method;
    }

    public static void main(String[] args) {
//        System.out.println(dbColumn2JavaField("_plan_status"));
    }
}
