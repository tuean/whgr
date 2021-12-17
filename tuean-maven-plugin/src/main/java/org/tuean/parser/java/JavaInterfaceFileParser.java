package org.tuean.parser.java;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.tuean.entity.define.JavaClass;
import org.tuean.entity.define.JavaMethod;
import org.tuean.parser.IParser;
import org.tuean.util.Log;
import org.tuean.util.ParserJavaUtil;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JavaInterfaceFileParser implements IParser<org.tuean.entity.define.JavaClass> {

    @Override
    public JavaClass parser(InputStream in) {
        try {
            List<String> lines = IOUtils.readLines(in, Charset.defaultCharset());
            return parser(lines);
        } catch (Exception var) {
            Log.getLog().error(var);
            throw new RuntimeException("parse java file error");
        }
    }

    @Override
    public JavaClass parser(List<String> textLines) {
        int size = textLines.size(), startIndex = 0;
        boolean packageFlag = false;
        JavaClass javaClass = new JavaClass();

        // package info
        int annotation = -1;
        for (; size > startIndex; startIndex++) {
            String line = textLines.get(startIndex);
            if (annotation != 3) annotation = ParserJavaUtil.ignore(line);
            if (annotation > 0) continue;
            if (line.trim().startsWith("package ")) {
                String packageInfo = line.trim().replaceFirst("package ", "");
                javaClass.setPackageInfo(packageInfo);
                packageFlag = true;
                break;
            }
        }
        if (!packageFlag) throw new RuntimeException("no package info");

        // import list
        annotation = -1;
        List<String> importList = new ArrayList<>();
        for (; size > startIndex; startIndex++) {
            String line = textLines.get(startIndex);
            if (annotation != 3) annotation = ParserJavaUtil.ignore(line);
            if (annotation > 0) continue;
            if (line.trim().startsWith("package ")) throw new RuntimeException("multi package error");
            if (line.trim().startsWith("import ")) {
                importList.add(line.trim().replaceFirst("import ", ""));
            }
        }
        javaClass.setImportList(importList);

        // class
        annotation = -1;
        for (; size > startIndex; startIndex++) {
            String line = textLines.get(startIndex);
            if (annotation != 3) annotation = ParserJavaUtil.ignore(line);
            if (annotation > 0) continue;
            String[] ss = line.trim().split(" ");
            if ("public".equals(ss[0]) && "interface".equals(ss[1])) {
                String className = ss[2];
                javaClass.setClassName(className);
            } else {
                throw new RuntimeException("illegal class name");
            }
        }

        // method
        annotation = -1;
        StringBuffer sb = new StringBuffer();
        for (; size > startIndex; startIndex++) {
            String line = textLines.get(startIndex);
            if (annotation != 3) annotation = ParserJavaUtil.ignore(line);
            if (annotation > 0) continue;
            for (int i = 0; i < line.toCharArray().length; i++) {
                char t = line.charAt(i);
                sb.append(t);
                if (';' == t) {
                    String methodLine = sb.toString();
                    sb = new StringBuffer();
                    Log.getLog().info(methodLine);
                    JavaMethod method = ParserJavaUtil.parseInterfaceMethodStr(methodLine);
                    javaClass.addMethod(method);
                }
            }
        }

        return javaClass;
    }


}
