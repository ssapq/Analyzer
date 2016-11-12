package com.vmall.analyzer.synonym.core;

import com.vmall.analyzer.synonym.job.JobBuilder;
import com.vmall.analyzer.synonym.loader.DynamicSynonymWordsLoader;
import com.vmall.analyzer.synonym.util.Costants;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by shaosh on 2016/8/29.
 * 同义词词典
 * 同义词是一组多对多的词
 */
public class SynonymWordsDictionary {
    private static Logger logger = Logger.getLogger(SynonymWordsDictionary.class);
    private static volatile Map<String,List<String>> synonymWords;//

    /*
     * 同义词词典单子实例
     */
    private static SynonymWordsDictionary singleton;

    private SynonymWordsDictionary(){
        synonymWords = new HashMap<String, List<String>>();
        JobBuilder.getSingleton().startJob();
    }

    /**
     * 词典初始化
     */
    public static SynonymWordsDictionary initial(){
        logger.info(Costants.LOG_SIGN +"--------------SynonymWordsDictionary init start --------------------");
        if(singleton == null){
            synchronized(SynonymWordsDictionary.class){
                singleton = new SynonymWordsDictionary();
                return singleton;
            }
        }
        logger.info(Costants.LOG_SIGN +"--------------SynonymWordsDictionary init end --------------------");
        return singleton;
    }

    /**
     * 获取词典单子实例
     * @return Dictionary 单例对象
     */
    public static SynonymWordsDictionary getSingleton(){
        if(singleton == null){
            logger.info(Costants.LOG_SIGN +"-------------- singleton is null , init SynonymWordsDictionary --------------------");
            SynonymWordsDictionary.initial();
        }

        return singleton;
    }

    /**
     * 指示是否词库为空
     * @return
     */
    public boolean isSynonyWordsLoadFinieshed() {
        return  !(synonymWords == null);
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
