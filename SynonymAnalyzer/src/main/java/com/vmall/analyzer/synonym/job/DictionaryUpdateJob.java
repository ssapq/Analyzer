package com.vmall.analyzer.synonym.job;

import com.vmall.analyzer.synonym.core.SynonymWordsDictionary;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by shaosh on 2016/9/19.
 */
public class DictionaryUpdateJob implements Job{

    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try{
            SynonymWordsDictionary.getSingleton().refreshNewDict();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
