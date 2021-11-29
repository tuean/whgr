package org.tuean.entity.define;

public class JavaField {

    private JavaVisible javaVisible;

    private boolean isStatic;

    private boolean isFinal;

    private Class fieldClazz;

    private String fieldName;

    public JavaField() {
    }

    public JavaField(JavaVisible javaVisible, boolean isStatic, boolean isFinal, Class fieldClazz, String fieldName) {
        this.javaVisible = javaVisible;
        this.isStatic = isStatic;
        this.isFinal = isFinal;
        this.fieldClazz = fieldClazz;
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "JavaField{" +
                "javaVisible=" + javaVisible +
                ", isStatic=" + isStatic +
                ", isFinal=" + isFinal +
                ", fieldClazz=" + fieldClazz +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }

    public JavaVisible getJavaVisible() {
        return javaVisible;
    }

    public void setJavaVisible(JavaVisible javaVisible) {
        this.javaVisible = javaVisible;
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

    public Class getFieldClazz() {
        return fieldClazz;
    }

    public void setFieldClazz(Class fieldClazz) {
        this.fieldClazz = fieldClazz;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
