package com.vmall.search.analyzer.job;

import com.vmall.search.analyzer.util.PropertyUtil;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import com.vmall.search.analyzer.util.Constant;

/**
 * Created by shaosh on 2016/9/19.
 */
public class JobBuilder {
    private static Logger logger = Logger.getLogger(JobBuilder.class);
    private static JobBuilder singleton;

    public static JobBuilder getSingleton() {
        synchronized(JobBuilder.class){
            if(singleton == null){
                singleton = new JobBuilder();
                return singleton;
            }else{
                return singleton;
            }
        }
    }

    private JobBuilder(){
        
    }

    /**
     *
     */
    public void startJob(){
        Scheduler sched = null;
        try {
            SchedulerFactory schedFact = new StdSchedulerFactory();
            sched = schedFact.getScheduler();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        try {
            JobDetail job = org.quartz.JobBuilder.newJob(DictionaryUpdateJob.class)
                    .withIdentity("DictionaryUpdateJob", "DictionaryUpdateJobGroup")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("DictionaryUpdateTrigger", "DictionaryUpdateTriggerGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule(PropertyUtil.getCronExpression()))
                    .startNow()
                    .build();

            if(!sched.checkExists(job.getKey()) && !sched.checkExists(trigger.getKey())){
                sched.scheduleJob(job, trigger);
            }

            if(sched.isStarted()){
                sched.shutdown();
                if(sched.isShutdown()){
                    if(logger.isInfoEnabled()){
                        logger.info(Constant.LOG_SIGN + "---------- lexicon refresh Scheduler has shutdown ----------");
                    }
                    sched.startDelayed(30);
                }
                if(logger.isInfoEnabled()){
                    logger.info(Constant.LOG_SIGN + "---------- start lexicon refresh Scheduler ----------");
                }
            }else{
                sched.startDelayed(30);
                if(logger.isInfoEnabled()){
                    logger.info(Constant.LOG_SIGN + "---------- start lexicon refresh Scheduler ----------");
                }
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }

}
