package org.tuean.entity.define;

import java.util.List;

public class JavaMethod {

    private boolean isStatic;

    private boolean isFinal;

    private JavaVisible javaVisible;

    private String[] argNames;

    private Class[] argClazzs;

    private List<String> methodBody;


    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public JavaVisible getJavaVisible() {
        return javaVisible;
    }

    public void setJavaVisible(JavaVisible javaVisible) {
        this.javaVisible = javaVisible;
    }

    public String[] getArgNames() {
        return argNames;
    }

    public void setArgNames(String[] argNames) {
        this.argNames = argNames;
    }

    public Class[] getArgClazzs() {
        return argClazzs;
    }

    public void setArgClazzs(Class[] argClazzs) {
        this.argClazzs = argClazzs;
    }

    public List<String> getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(List<String> methodBody) {
        this.methodBody = methodBody;
    }
}
