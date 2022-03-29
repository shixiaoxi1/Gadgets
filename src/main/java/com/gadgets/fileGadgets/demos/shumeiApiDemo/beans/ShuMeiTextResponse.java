package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

import java.util.ArrayList;
import java.util.List;

public class ShuMeiTextResponse {
    private int code;
    private String message;
    private String requestId;
    private String riskLevel;
    private String riskLabel1;
    private String riskLabel2;
    private String riskLabel3;
    private String riskDescription;
    private RiskDetailBean riskDetail;
    private List<AllLabelBean> allLabels;
    private TokenLabelsBean tokenLabels;
    private AuxInfoBean auxInfo;
    private List<BusinessLabelBean> businessLabels;


    public ShuMeiTextResponse() {
        riskDetail = new RiskDetailBean();
        tokenLabels = new TokenLabelsBean();
        auxInfo = new AuxInfoBean();
        allLabels = new ArrayList<>();
        businessLabels = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
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

    public List<AllLabelBean> getAllLabels() {
        return allLabels;
    }

    public void setAllLabels(List<AllLabelBean> allLabels) {
        this.allLabels = allLabels;
    }

    public TokenLabelsBean getTokenLabels() {
        return tokenLabels;
    }

    public void setTokenLabels(TokenLabelsBean tokenLabels) {
        this.tokenLabels = tokenLabels;
    }

    public AuxInfoBean getAuxInfo() {
        return auxInfo;
    }

    public void setAuxInfo(AuxInfoBean auxInfo) {
        this.auxInfo = auxInfo;
    }

    public List<BusinessLabelBean> getBusinessLabels() {
        return businessLabels;
    }

    public void setBusinessLabels(List<BusinessLabelBean> businessLabels) {
        this.businessLabels = businessLabels;
    }

    @Override
    public String toString() {
        return "ShuMeiTextResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", requestId='" + requestId + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", riskLabel1='" + riskLabel1 + '\'' +
                ", riskLabel2='" + riskLabel2 + '\'' +
                ", riskLabel3='" + riskLabel3 + '\'' +
                ", riskDescription='" + riskDescription + '\'' +
                ", riskDetail=" + riskDetail +
                ", allLabels=" + allLabels +
                ", tokenLabels=" + tokenLabels +
                ", auxInfo=" + auxInfo +
                ", businessLabels=" + businessLabels +
                '}';
    }
}
