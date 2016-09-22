package com.vmall.analyzer.synonym.core;

/**
 * Created by shaosh on 2016/8/31.
 * 词组
 */
public class Word {
    private String originalWord;
    private String synonyWord;

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getSynonyWord() {
        return synonyWord;
    }

    public void setSynonyWord(String synonyWord) {
        this.synonyWord = synonyWord;
    }
}
