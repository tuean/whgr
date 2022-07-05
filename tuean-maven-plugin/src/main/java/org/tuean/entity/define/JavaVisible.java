package org.tuean.entity.define;

import org.apache.commons.lang.StringUtils;
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
        if (JavaVisible.visibleEmpty().equals(visible)) return Consts.EMPTY_STR;
        if (StringUtils.isBlank(visible.getVisibleRange())) {
            return visible.getVisibleRange();
        }
        return visible.getVisibleRange() + Consts.BLANK_SPACE;
    }

    @Override
    public String toString() {
        return "JavaVisible{" +
                "visibleRange='" + visibleRange + '\'' +
                '}';
    }

    public static boolean isJavaVisible(String visible) {
        return StringUtils.isBlank(visible) || "public".equals(visible) || "private".equals(visible) || "protected".equals(visible);
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
