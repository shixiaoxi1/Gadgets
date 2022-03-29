package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

public class AllLabelBean {
    private String riskLabel1;
    private String riskLabel2;
    private String riskLabel3;
    private String riskDescription;
    private RiskDetailBean riskDetail;
    private float probability;
    private String riskLevel;

    public AllLabelBean() {
        riskDetail = new RiskDetailBean();
    }

    public String getRiskLabel1() {
        return riskLabel1;
    }

    public void setRiskLabel1(String riskLabel1) {
        this.riskLabel1 = riskLabel1;
    }

    public String getRiskLabel2() {
        return riskLabel2;
    }

    public void setRiskLabel2(String riskLabel2) {
        this.riskLabel2 = riskLabel2;
    }

    public String getRiskLabel3() {
        return riskLabel3;
    }

    public void setRiskLabel3(String riskLabel3) {
        this.riskLabel3 = riskLabel3;
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    public RiskDetailBean getRiskDetail() {
        return riskDetail;
    }

    public void setRiskDetail(RiskDetailBean riskDetail) {
        this.riskDetail = riskDetail;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Override
    public String toString() {
        return "AllLabelBean{" +
                "riskLabel1='" + riskLabel1 + '\'' +
                ", riskLabel2='" + riskLabel2 + '\'' +
                ", riskLabel3='" + riskLabel3 + '\'' +
                ", riskDescription='" + riskDescription + '\'' +
                ", riskDetail=" + riskDetail +
                ", probability=" + probability +
                ", riskLevel='" + riskLevel + '\'' +
                '}';
    }
}
