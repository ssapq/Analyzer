package com.vmall.analyzer.synonym.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by shaosh on 2016/9/19.
 */
public class JobBuilder {

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
            e.printStackTrace();
        }

        try {
            JobDetail job = org.quartz.JobBuilder.newJob(DictionaryUpdateJob.class)
                    .withIdentity("SynonymDictionaryUpdateJob", "SynonymDictionaryUpdateJobGroup")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("SynonymDictionaryUpdateTrigger", "SynonymDictionaryUpdateTriggerGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/40 * * * * ?"))
                    .startNow()
                    .build();
            sched.scheduleJob(job, trigger);
            sched.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
