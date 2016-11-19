package com.vmall.analyzer.synonym.thread;

import com.vmall.search.analyzer.cfg.DefaultConfig;
import com.vmall.search.analyzer.dic.DictionaryCore;
import org.apache.log4j.Logger;

/**
 * Created by shaosh on 2016/11/19.
 */
public class MonitorThread implements Runnable {
    private static Logger logger = Logger.getLogger(MonitorThread.class);


    public void run() {
        logger.info("monitor thread has started");
        initDictionary();
    }

    /**
     * 初始化词典
     */
    private void initDictionary(){
        try{
            logger.info("init dict start");
            DictionaryCore.initial(DefaultConfig.getInstance());
            logger.info("init dict end");
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    /**
     * 监控job
     */
    private void monitorJob(){
      
    }
}
