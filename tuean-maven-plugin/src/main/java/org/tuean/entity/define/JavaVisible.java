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

}
