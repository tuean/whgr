package org.tuean.entity;


import org.tuean.consts.JavaField;

public class DaoMethodConfig {

    private String methodName;

    private JavaField javaField;

    private Class[] argClass;

    private String[] argNames;



    public JavaField getJavaField() {
        return javaField;
    }

    public void setJavaField(JavaField javaField) {
        this.javaField = javaField;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getArgClass() {
        return argClass;
    }

    public void setArgClass(Class[] argClass) {
        this.argClass = argClass;
    }

    public String[] getArgNames() {
        return argNames;
    }

    public void setArgNames(String[] argNames) {
        this.argNames = argNames;
    }
}
