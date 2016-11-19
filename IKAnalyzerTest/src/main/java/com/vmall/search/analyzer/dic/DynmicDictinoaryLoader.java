package com.vmall.search.analyzer.dic;

import com.vmall.search.analyzer.cfg.DefaultConfig;

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
     * 整体替换字典
     * @return
     * @throws Exception
     */
    public boolean refreshDict() throws Exception{
        DictSegment newDict = DictionaryHelper.getSingleton().getMainDict(DefaultConfig.getInstance(),null,true);
        if(newDict == null){
            return false;
        }

        newDict = DictionaryHelper.getSingleton().getExtDictFromDB(newDict,true);
        if(newDict == null){
            return false;
        }

        newDict = DictionaryHelper.getSingleton().getSynonymDictFromDB(newDict,true);
        if(newDict == null){
            return false;
        }

        newDict = DictionaryHelper.getSingleton().getSearchAttributesFromDB(newDict);
        if(newDict == null){
            return false;
        }

        Dictionary.getSingleton().set_MainDict(newDict);
        return true;
    }


}
