package org.tuean.entity.define;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

public class JavaMethod {

    private String methodName;

    private boolean isStatic;

    private boolean isFinal;

    private boolean voidFlag;

    private boolean interfaceMethod;

    private Class returnClass;

    private String returnClassStr;

    private JavaVisible javaVisible;

//    private String[] argNames;

//    private String[] argClassStrs;
//    private Class[] argClazzs;
    private List<JavaMethodArgs> args;

    private List<String> methodBody;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof JavaMethod) {
            JavaMethod o = (JavaMethod) obj;
            boolean nameFlag = StringUtils.equals(o.getMethodName(), this.getMethodName());
            boolean returnClassFlag = StringUtils.equals(o.getReturnClassStr(), this.getReturnClassStr());
            // todo check return class and args if equals
            return nameFlag;
        }
        return false;
    }

    @Override
    public String toString() {
        return "JavaMethod{" +
                "methodName='" + methodName + '\'' +
                ", isStatic=" + isStatic +
                ", isFinal=" + isFinal +
                ", voidFlag=" + voidFlag +
                ", interfaceMethod=" + interfaceMethod +
                ", returnClass=" + returnClass +
                ", returnClassStr='" + returnClassStr + '\'' +
                ", javaVisible=" + javaVisible +
                ", args=" + args +
                ", methodBody=" + methodBody +
                '}';
    }

    public String getReturnClassStr() {
        return returnClassStr;
    }

    public void setReturnClassStr(String returnClassStr) {
        this.returnClassStr = returnClassStr;
    }

    public boolean isInterfaceMethod() {
        return interfaceMethod;
    }

    public void setInterfaceMethod(boolean interfaceMethod) {
        this.interfaceMethod = interfaceMethod;
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


    public List<String> getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(List<String> methodBody) {
        this.methodBody = methodBody;
    }

    public List<JavaMethodArgs> getArgs() {
        return args;
    }

    public void setArgs(List<JavaMethodArgs> args) {
        this.args = args;
    }
}
