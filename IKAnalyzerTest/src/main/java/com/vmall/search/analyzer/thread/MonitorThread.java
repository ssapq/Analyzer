package com.vmall.search.analyzer.thread;

import com.vmall.search.analyzer.solr.IKTokenizerFactory;
import org.apache.log4j.Logger;

/**
 * Created by shaosh on 2016/11/19.
 */
public class MonitorThread implements Runnable {
    private static Logger logger = Logger.getLogger(MonitorThread.class);


    public void run() {
        logger.info("monitor thread has started");
    }
}
