package org.tuean.entity.define;

import java.util.Arrays;
import java.util.List;

public class JavaMethod {

    private String methodName;

    private boolean isStatic;

    private boolean isFinal;

    private boolean voidFlag;

    private boolean interfaceMethod;

    private Class returnClass;

    private JavaVisible javaVisible;

    private String[] argNames;

    private String[] argClassStrs;
    private Class[] argClazzs;

    private List<String> methodBody;


    @Override
    public String toString() {
        return "JavaMethod{" +
                "methodName='" + methodName + '\'' +
                ", isStatic=" + isStatic +
                ", isFinal=" + isFinal +
                ", voidFlag=" + voidFlag +
                ", interfaceMethod=" + interfaceMethod +
                ", returnClass=" + returnClass +
                ", javaVisible=" + javaVisible +
                ", argNames=" + Arrays.toString(argNames) +
                ", argClassStrs=" + Arrays.toString(argClassStrs) +
                ", argClazzs=" + Arrays.toString(argClazzs) +
                ", methodBody=" + methodBody +
                '}';
    }

    public boolean isInterfaceMethod() {
        return interfaceMethod;
    }

    public void setInterfaceMethod(boolean interfaceMethod) {
        this.interfaceMethod = interfaceMethod;
    }

    public String[] getArgClassStrs() {
        return argClassStrs;
    }

    public void setArgClassStrs(String[] argClassStrs) {
        this.argClassStrs = argClassStrs;
    }

    public boolean isVoidFlag() {
        return voidFlag;
    }

    public void setVoidFlag(boolean voidFlag) {
        this.voidFlag = voidFlag;
    }

    public Class getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class returnClass) {
        this.returnClass = returnClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

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
