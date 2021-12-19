package org.tuean.util;

import org.apache.commons.lang.StringUtils;
import org.tuean.entity.define.JavaAnnotation;
import org.tuean.entity.define.JavaMethod;
import org.tuean.entity.define.JavaMethodArgs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.tuean.consts.Consts.ARG_DEFAULT;

public class ParserJavaUtil {

    public static int ignore(String line) {
        if (StringUtils.isBlank(line) || line.trim().startsWith("//") ) {
            return 1;
        } else if (line.trim().startsWith("/**")) {
            return 2;
        } else if (line.trim().startsWith("**/")) {
            return 3;
        }

        return 0;
    }


    public static JavaMethod parseInterfaceMethodStr(String s) {
        if (s == null) return null;
        JavaMethod method = new JavaMethod();
        method.setInterfaceMethod(true);
        StringBuffer sb = new StringBuffer();
        int index = 0;
        s = s.trim();
        // start with public, remove public
        s = s.startsWith("public ") ? s.substring(7, s.length() - 1).trim() : s;

        // return class
        for (int x = 0; x < s.toCharArray().length; x++) {
            char t = s.charAt(x);
            if (t == ' ') {
                if (sb.length() < 1) continue;
                String str = sb.toString();
                if ("void".equals(str)) {
                    method.setVoidFlag(true);
                } else {
                    method.setVoidFlag(false);
                    method.setReturnClassStr(str);
                }
                index = x;
                break;
            } else {
                sb.append(t);
            }
        }

        // method name
        sb = new StringBuffer();
        for (int i = index; i < s.toCharArray().length; i++) {
            if ('(' != s.charAt(i)) {
                sb.append(s.charAt(i));
            } else {
                method.setMethodName(sb.toString());
                index = i;
                break;
            }
        }

        // args
        List<JavaMethodArgs> args = new LinkedList<>();
        String subS = s.trim().substring(index, s.length() - 1);
        subS = Util.removeByFirst(subS, "(");
        subS = Util.removeByEnd(subS, ";");
        subS = Util.removeByEnd(subS, ")");
        String[] subSs = subS.split(",");
        for (String ss : subSs) {
            if (StringUtils.isBlank(ss)) continue;
            JavaMethodArgs arg = new JavaMethodArgs();
            ss = ss.trim();
            boolean annotationFlag = false, fieldFlag = true;
            int wordIndex = 0;
            JavaAnnotation annotation = new JavaAnnotation();
            sb = new StringBuffer();
            for (int i = 0; i < ss.length(); i++) {
                char t = ss.charAt(i);
                if (t == '@') {
                    annotationFlag = true;
                    fieldFlag = false;
//                    sb.append(t);
                } else if (t == '(' && annotationFlag){
                    String annotationName = sb.toString();
                    annotation.setAnnotationName(annotationName);
                    sb = new StringBuffer();
                } else if (t == ')' && annotationFlag) {
                    fieldFlag = true;
                    String aT = sb.toString();
                    boolean hasEqual = aT.contains("=");
                    if (!hasEqual) {
                        String defaultValue = aT.replace("\"", "");
                        annotation.addAttribute(ARG_DEFAULT, defaultValue);
                    } else {
                        String[] vals = aT.split("=");
                        String k = vals[0].replace("\"", "");
                        String v= vals[1].replace("\"", "");
                        annotation.addAttribute(k, v);
                    }
                    sb = new StringBuffer();
                    arg.setAnnotation(annotation);
                    annotationFlag = false;
                } else if (t == ' ') {
                    if (sb.length() < 1) continue;
                    String str = sb.toString();
                    if (wordIndex == 0) {
                        arg.setArgClassStr(str);
                        sb = new StringBuffer();
                    }
                    if (wordIndex == 1) {
                        arg.setArgName(str);
                        args.add(arg);
                        sb = new StringBuffer();
                    }
                    wordIndex++;
                } else {
                    sb.append(t);
                }
            }
            if (sb.length() > 0) {
                String str = sb.toString();
                arg.setArgName(str);
                args.add(arg);
            }

            method.setArgs(args);
        }

        return method;
    }


}
