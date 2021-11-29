package org.tuean.init;

import org.tuean.entity.define.JavaClass;
import org.tuean.util.Util;

public class Init {

    public static JavaClass defaultSetting(String className) {
        JavaClass javaClass = new JavaClass();
        javaClass.setClassName(Util.uppercaseFirst(className));
        return javaClass;
    }

}
