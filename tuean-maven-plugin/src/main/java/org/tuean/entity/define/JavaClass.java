package org.tuean.entity.define;


import java.util.List;

public class JavaClass {

    private String packageInfo;

    private List<String> importList;

    private String className;

    private List<JavaMethod> methodList;

    private List<JavaField> fieldList;





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
