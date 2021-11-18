package org.tuean.init;

import org.tuean.entity.define.JavaClass;

public class Init {

    public static JavaClass defaultSetting(String className) {
        JavaClass javaClass = new JavaClass();
        javaClass.setClassName(className);
        return javaClass;
    }

}
