package com.vmall.analyzer.synonym.loader;

import com.vmall.analyzer.synonym.core.SynonymWordsDictionary;
import com.vmall.analyzer.synonym.core.Word;
import com.vmall.analyzer.synonym.db.SynonymwordDBDao;
import com.vmall.analyzer.synonym.job.JobBuilder;
import com.vmall.analyzer.synonym.util.Costants;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by shaosh on 2016/8/29.
 */
public class DynamicSynonymWordsLoader {
    private static Logger logger = Logger.getLogger(DynamicSynonymWordsLoader.class);
    private static DynamicSynonymWordsLoader singleton;

    private DynamicSynonymWordsLoader(){

    }
    /**
     * DynmicDictinoaryLoader实例
     * @return Dictionary 单例对象
     */
    public static DynamicSynonymWordsLoader getSingleton(){
        synchronized(DynamicSynonymWordsLoader.class){
            if(singleton == null){
                singleton = new DynamicSynonymWordsLoader();
                return singleton;
            }else{
                return singleton;
            }
        }
    }

    /**
     * 加载同义词
     */
    public Map<String,List<String>> refreshSynonymWords() throws Exception{
        logger.info(Costants.LOG_SIGN + "---------start refresh synony words --------------");
        Map<String,List<String>> newDict = new HashMap<String, List<String>>();
        SynonymwordDBDao synonymwordDBDao = new SynonymwordDBDao();
        List<Word> synonymWordList = synonymwordDBDao.getKeywords();

        if(synonymWordList == null || synonymWordList.isEmpty()){
            return newDict;
        }

        for(Word word : synonymWordList){
            if(word == null){
                continue;
            }

            String originalWords = word.getOriginalWord();
            String synonyWords = word.getSynonyWord();

            if(originalWords == null || originalWords.isEmpty()){
                continue;
            }
            if(synonyWords == null || synonyWords.isEmpty()){
                continue;
            }

            String[] oringinalWordArray = originalWords.split(";");
            String[] synonyWordArray = synonyWords.split(";");

            if(oringinalWordArray.length == 0){
                continue;
            }
            if(synonyWordArray.length == 0){
                continue;
            }

            for(String originalWord : oringinalWordArray){
                if(originalWord == null || originalWord.isEmpty()){
                    continue;
                }

                if(originalWord == null || originalWord.isEmpty()){
                    continue;
                }

                newDict.put(originalWord,Arrays.asList(synonyWordArray));
            }

        }
        logger.info(Costants.LOG_SIGN + "--------- refresh synony words end, load " + synonymWordList.size()+ " words--------------");
        return newDict;
    }

    public void loadSynonymWords(){
        SynonymWordsDictionary.getSingleton().loadSynonymWords();
    }
}
