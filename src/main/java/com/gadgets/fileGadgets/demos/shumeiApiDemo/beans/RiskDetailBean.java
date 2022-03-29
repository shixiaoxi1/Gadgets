package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

import java.util.ArrayList;
import java.util.List;

public class RiskDetailBean {

    private List<MatchedBean> matchedLists;
    private List<RiskSegmentBean> riskSegments;

    public RiskDetailBean() {
        matchedLists = new ArrayList<>();
        riskSegments = new ArrayList<>();
    }

    public List<MatchedBean> getMatchedLists() {
        return matchedLists;
    }

    public void setMatchedLists(List<MatchedBean> matchedLists) {
        this.matchedLists = matchedLists;
    }

    public List<RiskSegmentBean> getRiskSegments() {
        return riskSegments;
    }

    public void setRiskSegments(List<RiskSegmentBean> riskSegments) {
        this.riskSegments = riskSegments;
    }

    @Override
    public String toString() {
        return "RiskDetailBean{" +
                "matchedLists=" + matchedLists +
                ", riskSegments=" + riskSegments +
                '}';
    }
}
