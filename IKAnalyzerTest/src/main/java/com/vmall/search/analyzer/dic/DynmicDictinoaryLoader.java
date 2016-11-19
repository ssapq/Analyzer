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
        //分别初始化碎片化字典和非碎片化字典
        DictionaryCore.getSingleton().set_FragmentMainDict(this.buildSegment(true));
        DictionaryCore.getSingleton().set_NoFragmentMainDict(this.buildSegment(false));
        return true;
    }

    /**
     *
     * @param useFramgnet
     * @return
     * @throws Exception
     */
    private DictSegment buildSegment(boolean useFramgnet) throws Exception{
        DictSegment newDict = DictionaryHelper.getSingleton().getMainDict(DefaultConfig.getInstance(),null,useFramgnet);
        if(newDict == null){
            return new DictSegment((char)0);
        }

        newDict = DictionaryHelper.getSingleton().getExtDictFromDB(newDict,useFramgnet);
        if(newDict == null){
            return new DictSegment((char)0);
        }

        newDict = DictionaryHelper.getSingleton().getSynonymDictFromDB(newDict,useFramgnet);
        if(newDict == null){
            return new DictSegment((char)0);
        }

        newDict = DictionaryHelper.getSingleton().getSearchAttributesFromDB(newDict);
        if(newDict == null){
            return new DictSegment((char)0);
        }
        return newDict;
    }

}
