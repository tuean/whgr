package org.tuean.entity.define;

import org.tuean.consts.Consts;

import java.util.List;

public class JavaMethodArgs {

    private int index;

    private String argName;

    private Class argClass;
    private String argClassStr;

    private JavaAnnotation annotation;


    @Override
    public String toString() {
        return "JavaMethodArgs{" +
                "index=" + index +
                ", argName='" + argName + '\'' +
                ", argClass=" + argClass +
                ", argClassStr='" + argClassStr + '\'' +
                ", annotation=" + annotation +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }

    public Class getArgClass() {
        return argClass;
    }

    public void setArgClass(Class argClass) {
        this.argClass = argClass;
    }

    public String getArgClassStr() {
        return argClassStr;
    }

    public void setArgClassStr(String argClassStr) {
        this.argClassStr = argClassStr;
    }

    public JavaAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(JavaAnnotation annotation) {
        this.annotation = annotation;
    }
}
