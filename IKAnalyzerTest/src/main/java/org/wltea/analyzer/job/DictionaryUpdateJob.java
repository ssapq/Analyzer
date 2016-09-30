package org.wltea.analyzer.job;

import com.mysql.jdbc.Connection;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.wltea.analyzer.db.KeywordDBDao;
import org.wltea.analyzer.dic.DynmicDictinoaryLoader;
import org.wltea.analyzer.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
//        KeywordDBDao keywordDBDao = new KeywordDBDao();
//        List<String> addKeywordList = new ArrayList<String>();
//        List<String> keywords = keywordDBDao.getKeywords();
//        if(keywords == null || keywords.isEmpty()){
//            return;
//        }
//
//        for(String keyword : keywords){
//            if(keyword == null || keyword.isEmpty()){
//                continue;
//            }
//
//            String[] keywordArrays = keyword.split(";");
//            for(String keywordSplited : keywordArrays){
//                if(keywordSplited == null || keywordSplited.isEmpty()){
//                    continue;
//                }
//
//                addKeywordList.add(keywordSplited);
//            }
//        }
//
//        try{
//            DynmicDictinoaryLoader.getSingleton().doAddWords(addKeywordList);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        try{
            DynmicDictinoaryLoader.getSingleton().refreshDict();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
