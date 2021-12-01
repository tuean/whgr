package org.tuean.entity.define;


import java.util.List;

public class JavaClass {

    private String packageInfo;

    private List<String> importList;

    private String classType;

    private String className;

    private List<JavaMethod> methodList;

    private List<JavaField> fieldList;


    @Override
    public String toString() {
        return "JavaClass{" +
                "packageInfo='" + packageInfo + '\'' +
                ", importList=" + importList +
                ", classType='" + classType + '\'' +
                ", className='" + className + '\'' +
                ", methodList=" + methodList +
                ", fieldList=" + fieldList +
                '}';
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public List<String> getImportList() {
        return importList;
    }

    public void setImportList(List<String> importList) {
        this.importList = importList;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<JavaMethod> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<JavaMethod> methodList) {
        this.methodList = methodList;
    }

    public List<JavaField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<JavaField> fieldList) {
        this.fieldList = fieldList;
    }
}
