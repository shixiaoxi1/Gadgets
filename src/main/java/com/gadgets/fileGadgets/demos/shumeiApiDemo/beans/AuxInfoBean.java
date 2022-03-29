package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

import java.util.ArrayList;
import java.util.List;

public class AuxInfoBean {
    private String filteredText;
    private List<ContactResultBean> contactResult;

    public AuxInfoBean() {
        contactResult = new ArrayList<>();
    }

    public String getFilteredText() {
        return filteredText;
    }

    public void setFilteredText(String filteredText) {
        this.filteredText = filteredText;
    }

    public List<ContactResultBean> getContactResult() {
        return contactResult;
    }

    public void setContactResult(List<ContactResultBean> contactResult) {
        this.contactResult = contactResult;
    }

    @Override
    public String toString() {
        return "AuxInfoBean{" +
                "filteredText='" + filteredText + '\'' +
                ", contactResult=" + contactResult +
                '}';
    }
}
