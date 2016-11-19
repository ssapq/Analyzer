package com.vmall.analyzer.synonym.thread;

import com.vmall.analyzer.synonym.core.SynonymWordsDictionary;
import org.apache.log4j.Logger;

/**
 * Created by shaosh on 2016/11/19.
 */
public class MonitorThread implements Runnable {
    private static Logger logger = Logger.getLogger(MonitorThread.class);


    public void run() {
        logger.info("synonym monitor thread has started");
        initDictionary();
    }

    /**
     * 初始化词典
     */
    private void initDictionary(){
        try{
            logger.info("init synonym dict start");
            SynonymWordsDictionary.initial();
            logger.info("init synonym dict end");
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
