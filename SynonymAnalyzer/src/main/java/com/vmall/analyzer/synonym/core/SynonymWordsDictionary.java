package com.vmall.analyzer.synonym.core;

import com.vmall.analyzer.synonym.loader.DynamicSynonymWordsLoader;

import java.util.*;

/**
 * Created by shaosh on 2016/8/29.
 * 同义词词典
 * 同义词是一组多对多的词
 */
public class SynonymWordsDictionary {
    private static volatile Map<String,List<String>> synonymWords;//

    /*
     * 同义词词典单子实例
     */
    private static SynonymWordsDictionary singleton;

    static {
        synonymWords = new HashMap<String, List<String>>();
    }

    private SynonymWordsDictionary(){
    }

    /**
     * 词典初始化
     */
    public static SynonymWordsDictionary initial(){
        if(synonymWords == null){
            synonymWords = new HashMap<String, List<String>>();
        }

        if(singleton == null){
            synchronized(SynonymWordsDictionary.class){
                if(singleton == null){
                    singleton = new SynonymWordsDictionary();
                    return singleton;
                }
            }
        }
        return singleton;
    }

    /**
     * 获取词典单子实例
     * @return Dictionary 单例对象
     */
    public static SynonymWordsDictionary getSingleton(){
        if(singleton == null){
            SynonymWordsDictionary.initial();
        }

        return singleton;
    }

    /**
     * 指示是否词库为空
     * @return
     */
    public boolean isSynonyWordsLoadFinieshed() {
        return  !synonymWords.isEmpty();
    }

    /**
     *
     * @return
     */
    public List<String> getWordList(String originalWord){
        if(originalWord == null || originalWord.isEmpty()){
            return null;
        }

        return synonymWords.get(originalWord);
    }

    /**
     * 增加词
     * @throws Exception
     */
   public boolean addWord(String standardWord,String synonymWord) throws Exception{
        if(standardWord == null || standardWord.isEmpty()){
            return false;
        }

        if(synonymWord == null || synonymWord.isEmpty()){
            return false;
        }

        synchronized (synonymWords){
            List<String> synonymList = synonymWords.get(standardWord);
            if(synonymList == null){
                synonymList = new ArrayList<String>();
                synonymList.add(synonymWord);
                synonymWords.put(standardWord,synonymList);
            }else{
                synonymList.add(synonymWord);
            }
        }
        return true;
   }

    /**
     * 增加词组
     * @throws Exception
     */
    public boolean addWords(String standardWord,List<String> synonymWordList) throws Exception{
        if(standardWord == null || standardWord.isEmpty()){
            return false;
        }

        if(synonymWordList == null || synonymWordList.isEmpty()){
            return false;
        }

        synchronized (synonymWords) {
            List<String> synonymList = synonymWords.get(standardWord);
            if (synonymList == null) {
                synonymList = new ArrayList<String>();
                synonymList.addAll(synonymWordList);
                synonymWords.put(standardWord, synonymList);
            } else {
                synonymList.addAll(synonymWordList);
            }
        }
        return true;
    }

    /**
     * 删除词
     * @throws Exception
     */
    public boolean deleteWord(String standardWord,String synonymWord) throws Exception{
        if(standardWord == null || standardWord.isEmpty()){
            return false;
        }

        if(synonymWord == null || synonymWord.isEmpty()){
            return false;
        }

        synchronized (synonymWords) {
            List<String> synonymList = synonymWords.get(standardWord);
            if (synonymList == null || synonymList.isEmpty()) {
                return false;
            } else {
                synonymList.remove(synonymWord);
            }
        }
        return true;
    }

    /**
     * 删除词
     * @throws Exception
     */
    public boolean deleteWords(String standardWord,List<String> synonymWordList) throws Exception{
        if(standardWord == null || standardWord.isEmpty()){
            return false;
        }

        if(synonymWordList == null || synonymWordList.isEmpty()){
            return false;
        }

        synchronized (synonymWords) {
            List<String> synonymList = synonymWords.get(standardWord);
            if (synonymList == null || synonymList.isEmpty()) {
                return false;
            } else {
                synonymList.removeAll(synonymWordList);
            }
        }
        return true;
    }

    /**
     * 修改词
     * @throws Exception
     */
    public boolean modifyWord(String standardWord,String oldWord,String newWord) throws Exception{
        if(standardWord == null || standardWord.isEmpty()){
            return false;
        }

        if(oldWord == null || oldWord.isEmpty()){
            return false;
        }

        if(newWord == null || newWord.isEmpty()){
            return false;
        }

        synchronized (synonymWords) {
            List<String> synonymList = synonymWords.get(standardWord);
            if (synonymList == null || synonymList.isEmpty()) {
                return false;
            } else {
                synonymList.remove(oldWord);
                synonymList.add(newWord);
            }
        }
        return true;
    }

    /**
     * 加载新的字典
     */
    public boolean refreshNewDict() throws Exception{
        synonymWords = DynamicSynonymWordsLoader.getSingleton().refreshSynonymWords();
        return true;
    }
}
