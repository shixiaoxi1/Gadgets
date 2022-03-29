package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

public class TokenLabelsBean {
    private UGCAccountRiskBean UGC_account_risk;

    public TokenLabelsBean() {
        UGC_account_risk = new UGCAccountRiskBean();
    }

    public UGCAccountRiskBean getUGC_account_risk() {
        return UGC_account_risk;
    }

    public void setUGC_account_risk(UGCAccountRiskBean UGC_account_risk) {
        this.UGC_account_risk = UGC_account_risk;
    }

    @Override
    public String toString() {
        return "TokenLabelsBean{" +
                "UGC_account_risk=" + UGC_account_risk +
                '}';
    }
}
