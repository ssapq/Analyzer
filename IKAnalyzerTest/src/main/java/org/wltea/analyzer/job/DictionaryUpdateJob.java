package org.wltea.analyzer.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.wltea.analyzer.dic.DynmicDictinoaryLoader;
import org.wltea.analyzer.util.Constant;
import org.wltea.analyzer.util.PropertyUtil;

/**
 * Created by shaosh on 2016/9/19.
 */
public class DictionaryUpdateJob implements Job{
    private static Logger logger = Logger.getLogger(DictionaryUpdateJob.class);

    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if(logger.isInfoEnabled()) {
            logger.info(Constant.LOG_SIGN + "---------- refresh dictonary job start ----------");
        }
        if(!PropertyUtil.isLoadFromDb()){
            return;
        }

        try{
            DynmicDictinoaryLoader.getSingleton().refreshDict();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(logger.isInfoEnabled()) {
            logger.info(Constant.LOG_SIGN + "---------- refresh dictonary job end ----------");
        }
    }

}
