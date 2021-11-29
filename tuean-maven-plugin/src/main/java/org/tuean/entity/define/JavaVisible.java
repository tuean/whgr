package org.tuean.entity.define;

import org.tuean.consts.Consts;

public class JavaVisible {

    private String visibleRange;

    public String getVisibleRange() {
        return visibleRange;
    }

    public void setVisibleRange(String visibleRange) {
        this.visibleRange = visibleRange;
    }

    public static String getVisibleString(JavaVisible visible) {
        if (visible == null) return Consts.EMPTY_STR;
        return visible + Consts.BLANK_SPACE;
    }

    @Override
    public String toString() {
        return "JavaVisible{" +
                "visibleRange='" + visibleRange + '\'' +
                '}';
    }

    public JavaVisible(String visibleRange) {
        this.visibleRange = visibleRange;
    }

    public JavaVisible() {
    }

    public static JavaVisible visiblePrivate() {
        return new JavaVisible("private");
    }

    public static JavaVisible visiblePublic() {
        return new JavaVisible("public");
    }

    public static JavaVisible visibleProtected() {
        return new JavaVisible("protected");
    }

    public static JavaVisible visibleEmpty() {
        return new JavaVisible("");
    }

}
