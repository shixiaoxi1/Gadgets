package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

import java.util.ArrayList;
import java.util.List;

public class MatchedBean {
    private String name;
    private List<WordBean> words;

    public MatchedBean() {
        words = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WordBean> getWords() {
        return words;
    }

    public void setWords(List<WordBean> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return "MatchedBean{" +
                "name='" + name + '\'' +
                ", words=" + words +
                '}';
    }
}
