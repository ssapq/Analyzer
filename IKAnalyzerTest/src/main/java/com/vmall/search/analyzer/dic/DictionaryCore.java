package com.vmall.search.analyzer.dic;

import com.vmall.search.analyzer.cfg.Configuration;
import com.vmall.search.analyzer.job.JobBuilder;
import com.vmall.search.analyzer.util.Constant;
import com.vmall.search.analyzer.util.PropertyUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaosh on 2016/11/19.
 */
public class DictionaryCore {
    private static Logger logger = Logger.getLogger(DictionaryCore.class);

    /*
	 * 词典单子实例
	 */
    private static DictionaryCore singleton;

    /*
     * 主词典对象
     */
    private DictSegment _FragmentMainDict;

    /*
     * 不碎片化分词的词典
     */
    private DictSegment _NoFragmentMainDict;

    /*
     * 停止词词典
     */
    private DictSegment _StopWordDict;
    /*
     * 量词词典
     */
    private DictSegment _QuantifierDict;

    /*
     * 配置对象
     */
    private Configuration cfg;

    public DictSegment get_FragmentMainDict() {
        return _FragmentMainDict;
    }

    public DictSegment get_NoFragmentMainDict() {
        return _NoFragmentMainDict;
    }

    public DictSegment get_StopWordDict() {
        return _StopWordDict;
    }

    public DictSegment get_QuantifierDict() {
        return _QuantifierDict;
    }

    private DictionaryCore(Configuration cfg){
        this.cfg = cfg;
        this.loadMainDict();
        this.loadStopWordDict();
        this.loadQuantifierDict();
        this.loadExtDictFromDB();
        this.loadSynonymFromDB();
        this.loadSearchAttributesFromDB();
        JobBuilder.getSingleton().startJob();
    }


    /**
     * 词典初始化
     * 由于IK Analyzer的词典采用Dictionary类的静态方法进行词典初始化
     * 只有当Dictionary类被实际调用时，才会开始载入词典，
     * 这将延长首次分词操作的时间
     * 该方法提供了一个在应用加载阶段就初始化字典的手段
     * @return Dictionary
     */
    public static DictionaryCore initial(Configuration cfg){
        if(singleton == null){
            synchronized(Dictionary.class){
                if(singleton == null){
                    singleton = new DictionaryCore(cfg);
                    return singleton;
                }
            }
        }
        return singleton;
    }

    /**
     *	 加载碎片化的词典
     */
    public void set_FragmentMainDict(DictSegment newMainDict){
        this._FragmentMainDict = newMainDict;
    }

    /**
     *	 加载碎片化的词典
     */
    public void set_NoFragmentMainDict(DictSegment newMainDict){
        this._NoFragmentMainDict = newMainDict;
    }

    /**
     * 获取词典单子实例
     * @return Dictionary 单例对象
     */
    public static DictionaryCore getSingleton(){
        if(singleton == null){
            throw new IllegalStateException("词典尚未初始化，请先调用initial方法");
        }

        return singleton;
    }


    /**
     * 加载主词典及扩展词典
     */
    private void loadMainDict(){
        //是否开启主词典碎片化
        try{
            //建立一个主词典实例
            _FragmentMainDict = new DictSegment((char)0);
            _NoFragmentMainDict = new DictSegment((char)0);
            List<String> mainDict = DictionaryHelper.getSingleton().readMainDict(this.cfg);

            if(mainDict == null || mainDict.isEmpty()){
                return;
            }

            for(String word : mainDict){
                if(word == null || word.isEmpty()){
                    continue;
                }
                _FragmentMainDict.fillSegment(word.toLowerCase().toCharArray());
                _NoFragmentMainDict.fillSegment(word.toLowerCase().toCharArray());
            }
            DictionaryHelper.getSingleton().doFragment(mainDict,_FragmentMainDict);
        } catch (Exception e) {
            logger.error("Main Dictionary loading exception." + e.getMessage(),e);
        }finally{
            logger.info("--------------------load main dict end-----------------");
        }
    }

    /**
     * 从数据库加载词库
     */
    public void loadExtDictFromDB(){

        logger.info(Constant.LOG_SIGN + "-------------------------- load ext dict from db start --------------------------");
        try{
            List<String> extWordList = DictionaryHelper.getSingleton().readExtDict();
            if(extWordList == null || extWordList.isEmpty()){
                return;
            }

            int count = 0;
            for(String word : extWordList){
                if(word == null || word.isEmpty()){
                    continue;
                }
                count ++;
                _NoFragmentMainDict.fillSegment(word.trim().toLowerCase().toCharArray());
                _FragmentMainDict.fillSegment(word.trim().toLowerCase().toCharArray());
            }
            DictionaryHelper.getSingleton().doFragment(extWordList,_FragmentMainDict);

            logger.info(Constant.LOG_SIGN + "-------------------------- load ext dict num :"+ count +"--------------------------");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            logger.info(Constant.LOG_SIGN + "-------------------------- load ext dict from db end --------------------------");
        }
    }

    /**
     * 从数据库加载同义词库
     */
    public void loadSynonymFromDB(){
        logger.info(Constant.LOG_SIGN + "-------------------------- load synonym dict from db start --------------------------");

        int count = 0;
        try{
            List<String> synonymList = DictionaryHelper.getSingleton().readSynonymDict();
            for(String word : synonymList){
                if(word == null || word.isEmpty()){
                    continue;
                }
                count ++;
                _NoFragmentMainDict.fillSegment(word.trim().toLowerCase().toCharArray());
                _FragmentMainDict.fillSegment(word.trim().toLowerCase().toCharArray());
            }
            DictionaryHelper.getSingleton().doFragment(synonymList,_FragmentMainDict);

            logger.info(Constant.LOG_SIGN + "-------------------------- load synonym dict num :"+ count +"--------------------------");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            logger.info(Constant.LOG_SIGN + "-------------------------- load synonym dict from db end --------------------------");
        }
    }

    /**
     * 从搜索源加载词库
     */
    public void loadSearchAttributesFromDB(){
        if(!PropertyUtil.isLoadFromDb()){
            return;
        }

        logger.info(Constant.LOG_SIGN + "-------------------------- load search attributes dict from db start --------------------------");

        int count = 0;
        List<String> searchAttributes = new ArrayList<String>();

        try{
            searchAttributes = DictionaryHelper.getSingleton().readSearchAttributes();
            if(searchAttributes == null || searchAttributes.isEmpty()){
                return;
            }

            for(String searchAttribute : searchAttributes){
                if(searchAttribute == null || searchAttribute.isEmpty()){
                    continue;
                }
                _FragmentMainDict.fillSegment(searchAttribute.trim().toLowerCase().toCharArray());
                _NoFragmentMainDict.fillSegment(searchAttribute.trim().toLowerCase().toCharArray());
                count ++;
            }
            logger.info(Constant.LOG_SIGN + "-------------------------- load search attributes num :"+ count +"--------------------------");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            logger.info(Constant.LOG_SIGN + "-------------------------- load search attributes from db end --------------------------");
        }
    }

    /**
     * 加载用户扩展的停止词词典
     */
    private void loadStopWordDict(){
        //建立一个主词典实例
        _StopWordDict = new DictSegment((char)0);
        //加载扩展停止词典
        List<String> extStopWordDictFiles  = cfg.getExtStopWordDictionarys();
        if(extStopWordDictFiles != null){
            InputStream is = null;
            for(String extStopWordDictName : extStopWordDictFiles){
                System.out.println("加载扩展停止词典：" + extStopWordDictName);
                //读取扩展词典文件
                is = this.getClass().getClassLoader().getResourceAsStream(extStopWordDictName);
                //如果找不到扩展的字典，则忽略
                if(is == null){
                    continue;
                }
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
                    String theWord = null;
                    do {
                        theWord = br.readLine();
                        if (theWord != null && !"".equals(theWord.trim())) {
                            //System.out.println(theWord);
                            //加载扩展停止词典数据到内存中
                            _StopWordDict.fillSegment(theWord.trim().toLowerCase().toCharArray());
                        }
                    } while (theWord != null);

                } catch (IOException ioe) {
                    System.err.println("Extension Stop word Dictionary loading exception.");
                    ioe.printStackTrace();

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
            }
        }
    }

    /**
     * 加载量词词典
     */
    private void loadQuantifierDict(){
        //建立一个量词典实例
        _QuantifierDict = new DictSegment((char)0);
        //读取量词词典文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(cfg.getQuantifierDicionary());
        if(is == null){
            throw new RuntimeException("Quantifier Dictionary not found!!!");
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
            String theWord = null;
            do {
                theWord = br.readLine();
                if (theWord != null && !"".equals(theWord.trim())) {
                    _QuantifierDict.fillSegment(theWord.trim().toLowerCase().toCharArray());
                }
            } while (theWord != null);

        } catch (IOException ioe) {
            System.err.println("Quantifier Dictionary loading exception.");
            ioe.printStackTrace();

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
    }

}
