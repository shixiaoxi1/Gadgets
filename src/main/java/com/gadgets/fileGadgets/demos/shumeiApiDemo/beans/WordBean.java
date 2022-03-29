package com.gadgets.fileGadgets.demos.shumeiApiDemo.beans;

import java.util.Arrays;

public class WordBean {
    private String word;
    private int[] position;

    public WordBean() {
    }

    public WordBean(String word, int[] position) {
        this.word = word;
        this.position = position;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "WordBean{" +
                "word='" + word + '\'' +
                ", position=" + Arrays.toString(position) +
                '}';
    }
}
