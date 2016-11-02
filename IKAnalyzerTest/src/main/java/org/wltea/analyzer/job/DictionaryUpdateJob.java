package org.wltea.analyzer.job;

import com.mysql.jdbc.Connection;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.wltea.analyzer.db.KeywordDBDao;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.dic.DynmicDictinoaryLoader;
import org.wltea.analyzer.util.JdbcUtil;
import org.wltea.analyzer.util.PropertyUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaosh on 2016/9/19.
 */
public class DictionaryUpdateJob implements Job{
    private static Logger logger = Logger.getLogger(Dictionary.class);

    /**
     *
     * @param context
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("---------- refresh dictonary start ----------");
        if(!PropertyUtil.isLoadFromDb()){
            return;
        }

        try{
            DynmicDictinoaryLoader.getSingleton().refreshDict();
        }catch (Exception e){
            e.printStackTrace();
        }
        logger.info("---------- refresh dictonary end ----------");
    }

}
