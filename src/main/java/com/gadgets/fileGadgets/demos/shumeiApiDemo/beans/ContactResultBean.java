package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

public class ContactResultBean {
    private int contactType;
    private String contactString;

    public ContactResultBean() {
    }

    public int getContactType() {
        return contactType;
    }

    public void setContactType(int contactType) {
        this.contactType = contactType;
    }

    public String getContactString() {
        return contactString;
    }

    public void setContactString(String contactString) {
        this.contactString = contactString;
    }

    @Override
    public String toString() {
        return "ContactResultBean{" +
                "contactType=" + contactType +
                ", contactString='" + contactString + '\'' +
                '}';
    }
}
