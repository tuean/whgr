package org.tuean.entity.define;

import java.util.HashMap;
import java.util.Map;

public class JavaAnnotation {

    private String annotationName;

    private Map<String, Object> attributes;


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
