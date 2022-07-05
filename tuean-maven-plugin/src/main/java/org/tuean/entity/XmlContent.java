package org.tuean.entity;

public class XmlContent {

    private int blankSize;

    private String line;

    public XmlContent() {
    }

    public XmlContent(int blankSize, String line) {
        this.blankSize = blankSize;
        this.line = line;
    }

    public int getBlankSize() {
        return blankSize;
    }

    public void setBlankSize(int blankSize) {
        this.blankSize = blankSize;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
