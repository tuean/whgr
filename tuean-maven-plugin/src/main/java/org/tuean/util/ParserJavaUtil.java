package org.tuean.util;

import org.apache.commons.lang.StringUtils;
import org.tuean.entity.define.JavaMethod;

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
        for (int x = 0; x < s.toCharArray().length; x++) {
            char t = s.charAt(x);
            if (t == ' ') {
                if (sb.length() < 1) continue;
                String str = sb.toString();
                if (index == 0) {
                    if ("void".equals(str)) {
                        method.setVoidFlag(true);
                    } else {
                        method.setVoidFlag(false);
                        method.setReturnClassStr(str);
                    }
                }
                index++;
            } else {
                sb.append(t);
            }
        }
    }

}
