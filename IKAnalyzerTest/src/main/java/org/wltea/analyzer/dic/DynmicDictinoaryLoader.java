package org.wltea.analyzer.dic;

import org.wltea.analyzer.db.KeywordDBDao;
import org.wltea.analyzer.util.JdbcUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaosh on 2016/8/29.
 */
public class DynmicDictinoaryLoader {
    private static DynmicDictinoaryLoader singleton;

    private DynmicDictinoaryLoader(){

    }
    /**
     * DynmicDictinoaryLoader实例
     * @return Dictionary 单例对象
     */
    public static DynmicDictinoaryLoader getSingleton(){
        synchronized(DynmicDictinoaryLoader.class){
            if(singleton == null){
                singleton = new DynmicDictinoaryLoader();
                return singleton;
            }else{
                return singleton;
            }
        }
    }

    /**
     * 加载新词
     * @throws Exception
     */
    public boolean doAddWord(String newWord) throws Exception{
        if(newWord == null || newWord.isEmpty()){
            throw new Exception("newWord list is null");
        }
        List<String> newWordList = new ArrayList<String>();
        newWordList.add(newWord);
        Dictionary.getSingleton().addWords(newWordList);
        return true;
    }

    /**
     * 加载新词
     * @throws Exception
     */
    public boolean doAddWords(List<String> newWords) throws Exception{
        if(newWords == null || newWords.isEmpty()){
            throw new Exception("new words list is null");
        }
        Dictionary.getSingleton().addWords(newWords);
        return true;
    }

    /**
     * 删除新词
     * @return
     * @throws Exception
     */
    public boolean doDeleteWord(String deletedWord) throws Exception{
        if(deletedWord == null || deletedWord.isEmpty()){
            throw new Exception("delete words list is null");
        }
        List<String> deleteWords = new ArrayList<String>();
        deleteWords.add(deletedWord);
        Dictionary.getSingleton().disableWords(deleteWords);
        return true;
    }

    /**
     * 删除新词
     * @param deleteWords
     * @return
     * @throws Exception
     */
    public boolean doDeleteWords(List<String> deleteWords) throws Exception{
        if(deleteWords == null || deleteWords.isEmpty()){
            throw new Exception("delete words list is null");
        }
        Dictionary.getSingleton().disableWords(deleteWords);
        return true;
    }

    /**
     * 修改新词
     * @return
     * @throws Exception
     */
    public boolean doModifyWord(String oldWord,String newWord) throws Exception{
        if(oldWord == null || oldWord.isEmpty()){
            throw new Exception("oldWord is null");
        }

        if(newWord == null || newWord.isEmpty()){
            throw new Exception("newWords is null");
        }

        if(newWord.equals(oldWord)){
            return true;
        }

        List<String> oldWordList = new ArrayList<String>();
        List<String> newWordList = new ArrayList<String>();

        oldWordList.add(oldWord);
        newWordList.add(newWord);

        Dictionary.getSingleton().disableWords(oldWordList);
        Dictionary.getSingleton().addWords(newWordList);

        return true;
    }

}
