package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

public class BusinessLabelBean {
    private String businessLabel1;
    private String businessLabel2;
    private String businessLabel3;
    private String businessDescription;

    public BusinessLabelBean() {
    }

    public String getBusinessLabel1() {
        return businessLabel1;
    }

    public void setBusinessLabel1(String businessLabel1) {
        this.businessLabel1 = businessLabel1;
    }

    public String getBusinessLabel2() {
        return businessLabel2;
    }

    public void setBusinessLabel2(String businessLabel2) {
        this.businessLabel2 = businessLabel2;
    }

    public String getBusinessLabel3() {
        return businessLabel3;
    }

    public void setBusinessLabel3(String businessLabel3) {
        this.businessLabel3 = businessLabel3;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    @Override
    public String toString() {
        return "BusinessLabelBean{" +
                "businessLabel1='" + businessLabel1 + '\'' +
                ", businessLabel2='" + businessLabel2 + '\'' +
                ", businessLabel3='" + businessLabel3 + '\'' +
                ", businessDescription='" + businessDescription + '\'' +
                '}';
    }
}
