package com.vmall.search.analyzer.dic;

import com.vmall.search.analyzer.cfg.Configuration;
import com.vmall.search.analyzer.db.KeywordDBDao;
import com.vmall.search.analyzer.util.Constant;
import com.vmall.search.analyzer.util.PropertyUtil;
import com.vmall.search.analyzer.util.TextUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shaosh on 2016/11/18.
 */
public class DictionaryHelper {
    private static Logger logger = Logger.getLogger(DictionaryHelper.class);

    private DictionaryHelper(){}

    private static DictionaryHelper singleton;

    public static DictionaryHelper getSingleton(){
        if(singleton == null){
            singleton = new DictionaryHelper();
        }
        return singleton;
    }

    /**
     *	 加载词典
     */
    public DictSegment getMainDict(Configuration cfg,DictSegment newMainDict,boolean isFragment) throws Exception{
        if(cfg == null){
            return null;
        }

        if(newMainDict == null){
            newMainDict = new DictSegment((char)0);
        }

        List<String> wordList = this.readMainDict(cfg);
        if(wordList == null || wordList.isEmpty()){
            return null;
        }

        for(String word : wordList){
            if(word == null || word.isEmpty()){
                continue;
            }

            newMainDict.fillSegment(word.trim().toLowerCase().toCharArray());
        }
        if(isFragment){
            this.doFragment(wordList,newMainDict);
        }
        return newMainDict;
    }

    /**
     * 从数据库加载词库
     */
    public DictSegment getExtDictFromDB(DictSegment newMainDict,boolean isFragment) throws Exception{
        if(newMainDict == null){
            newMainDict = new DictSegment((char)0);
        }

        List<String> keywordList = this.readExtDict();
        if(keywordList == null || keywordList.isEmpty()){
            return newMainDict;
        }

        for(String keyword : keywordList){
            if(keyword == null || keyword.isEmpty()){
                continue;
            }
            newMainDict.fillSegment(keyword.trim().toLowerCase().toCharArray());
        }

        if(isFragment){
            this.doFragment(keywordList,newMainDict);
        }
        return newMainDict;
    }

    /**
     * 更新同义词词库
     * @return
     * @throws Exception
     */
    public DictSegment getSynonymDictFromDB(DictSegment newMainDict,boolean isFragment) throws Exception{
        if(newMainDict == null){
            newMainDict = new DictSegment((char)0);
        }

        List<String> synonymList = this.readSynonymDict();
        if(synonymList == null || synonymList.isEmpty()){
            return newMainDict;
        }

        for(String synonum : synonymList){
            if(synonum == null || synonum.isEmpty()){
                continue;
            }
            newMainDict.fillSegment(synonum.trim().toLowerCase().toCharArray());
        }

        if(isFragment){
            this.doFragment(synonymList,newMainDict);
        }
        return newMainDict;
    }

    /**
     * 更新同义词词库
     * @return
     * @throws Exception
     */
    public DictSegment getSearchAttributesFromDB(DictSegment newMainDict) throws Exception{
        if(newMainDict == null){
            newMainDict = new DictSegment((char)0);
        }
        List<String> searchAttributeList = this.readSearchAttributes();
        if(searchAttributeList == null || searchAttributeList.isEmpty()){
            return newMainDict;
        }

        for(String attr : searchAttributeList){
            if(attr == null || attr.isEmpty()){
                continue;
            }
            newMainDict.fillSegment(attr.trim().toLowerCase().toCharArray());
        }

        return newMainDict;
    }

    /**
     * 读取主字典
     * @return
     */
    public List<String> readMainDict(Configuration cfg){
        if(cfg == null){
            return null;
        }

        List<String> mainDictWordsList = new ArrayList<String>();
        //读取主词典文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cfg.getMainDictionary());
        if(is == null){
            throw new RuntimeException("Main Dictionary not found!!!");
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
            String theWord = null;
            do {
                theWord = br.readLine();
                if (theWord != null && !"".equals(theWord.trim())) {
                    mainDictWordsList.add(theWord);
                }

            } while (theWord != null);
        } catch (IOException ioe) {
            logger.error("Main Dictionary loading exception." + ioe.getMessage(),ioe);
        }finally{
            try {
                if(is != null){
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }

        return mainDictWordsList;
    }

    /**
     * 加载扩展词典
     */
    public List<String> readExtDict() throws Exception{
        if(!PropertyUtil.isLoadFromDb()){
            return null;
        }
        List<String> extDict = new ArrayList<String>();
        KeywordDBDao keywordDBDao = new KeywordDBDao();
        extDict = keywordDBDao.getKeywords();
        return extDict;
    }

    /**
     * 加载同义词库
     * @return
     */
    public List<String> readSynonymDict() throws Exception{
        if(!PropertyUtil.isLoadFromDb()){
            return null;
        }

        List<String> synonymList = new ArrayList<String>();
        List<String> resultList = new ArrayList<String>();
        KeywordDBDao keywordDBDao = new KeywordDBDao();
        synonymList = keywordDBDao.getSynonyms();
        if(synonymList == null || synonymList.isEmpty()){
            return null;
        }

        for(String keyword : synonymList){
            if(keyword == null || keyword.isEmpty()){
                continue;
            }

            String[] keywordArrays = keyword.split(";");
            for(String theWord : keywordArrays){
                if(theWord == null || theWord.isEmpty()){
                    continue;
                }
                resultList.add(theWord);
            }
        }
        return resultList;
    }

    /**
     *
     * @return
     */
    public List<String> readSearchAttributes() throws Exception{
        List<String> searchAttributes = new ArrayList<String>();
        KeywordDBDao keywordDBDao = new KeywordDBDao();
        searchAttributes = keywordDBDao.getSearchAttributes();
        return searchAttributes;
    }

    /**
     * 做碎片化
     * @param words
     * @param dict
     */
    public void doFragment(List<String> words,DictSegment dict) throws Exception{
        if(words == null || words.isEmpty()){
            return;
        }

        if(dict == null){
            return;
        }

        List<String> fragmentList = new ArrayList<String>();
        for(String word : words){
            if(word == null || word.isEmpty()){
                continue;
            }
            fragmentList.addAll(TextUtils.splitWord(word));
        }

        Set<String> fragmentSet = new HashSet<String>(fragmentList);
        for (String fragment : fragmentSet) {
            if (fragment == null || fragment.isEmpty()) {
                continue;
            }

            dict.fillSegment(fragment.trim().toLowerCase().toCharArray());
        }
    }

}
