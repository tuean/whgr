package org.tuean.entity.define;

import org.tuean.consts.Consts;

import java.util.HashMap;
import java.util.Map;

public class JavaAnnotation {

    private String annotationName;

    private Map<String, Object> attributes;

    public String toCodeStr() {
        if (this == null) return "";
        StringBuffer sb = new StringBuffer();
        sb.append(Consts.JAVA_ANNOTATION).append(this.getAnnotationName()).append(Consts.LEFT);
        if (attributes != null) {
            for (String key : this.attributes.keySet()) {
                Object val = this.attributes.get(key);
                if (Consts.ARG_DEFAULT.equals(key)) {
                    sb.append("\"").append(val).append("\"");
                    continue;
                }
                sb.append(key).append("=");
                if (val instanceof String || val instanceof Integer) {
                    sb.append(val);
                }
                if (val instanceof Class) {
                    Class v = (Class) val;
                    sb.append(v.getSimpleName() + ".class");
                }
            }
        }
        sb.append(Consts.RIGHT);
        return sb.toString();
    }


    public void addAttribute(String key, String value) {
        if (this.attributes == null) this.attributes = new HashMap<>();
        this.attributes.put(key, value);
    }

    @Override
    public String toString() {
        return "JavaAnnotation{" +
                "annotationName='" + annotationName + '\'' +
                ", attributes=" + attributes +
                '}';
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
