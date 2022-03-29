package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

import java.util.Arrays;

public class RiskSegmentBean {
    private String segment;
    private int[] position;

    public RiskSegmentBean() {
    }

    public RiskSegmentBean(String segment, int[] position) {
        this.segment = segment;
        this.position = position;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "RiskSegmentBean{" +
                "segment='" + segment + '\'' +
                ", position=" + Arrays.toString(position) +
                '}';
    }
}
