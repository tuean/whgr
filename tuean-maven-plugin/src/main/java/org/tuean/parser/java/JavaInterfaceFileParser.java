package org.tuean.parser.java;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.tuean.entity.define.JavaClass;
import org.tuean.parser.IParser;
import org.tuean.util.Log;

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
        for (; size > startIndex; startIndex++) {
            String line = textLines.get(startIndex);
            if (StringUtils.isAllBlank(line)) continue;
            if (line.trim().startsWith("package ")) {
                String packageInfo = line.trim().replaceFirst("package ", "");
                javaClass.setPackageInfo(packageInfo);
                packageFlag = true;
                break;
            }
        }
        if (!packageFlag) throw new RuntimeException("no package info");

        // import list
        List<String> importList = new ArrayList<>();
        for (; size > startIndex; startIndex++) {
            String line = textLines.get(startIndex);
            if (StringUtils.isAllBlank(line)) continue;
            if (line.trim().startsWith("package ")) throw new RuntimeException("multi package error");
            if (line.trim().startsWith("import ")) {
                importList.add(line.trim().replaceFirst("import ", ""));
            }
        }
        javaClass.setImportList(importList);

        // class
        for (; size > startIndex; startIndex++) {
            String line = textLines.get(startIndex);
            if (StringUtils.isAllBlank(line)) continue;

            String[] ss = line.trim().split(" ");
            if (ss[0] )
        }

        return javaClass;
    }


}
