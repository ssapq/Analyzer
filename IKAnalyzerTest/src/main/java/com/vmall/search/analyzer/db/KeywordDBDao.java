package com.vmall.search.analyzer.db;

import com.mysql.jdbc.Connection;
import com.vmall.search.analyzer.util.PropertyUtil;
import org.apache.log4j.Logger;
import com.vmall.search.analyzer.util.JdbcUtil;
import com.vmall.search.analyzer.util.TextUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shaosh on 2016/9/20.
 */
public class KeywordDBDao {
    private static Logger logger = Logger.getLogger(KeywordDBDao.class);
    /**
     * 获得词库关键词
     * @return
     */
    public List<String> getKeywords() {
        if(!PropertyUtil.isLoadFromDb()){
            return null;
        }

        List<String> keywordList = new ArrayList<String>();

        Connection conn = JdbcUtil.getSingleton().getConn();
        String sql = "select keyword from search_lexicon_t";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                keywordList.add(rs.getString(1));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return keywordList;
    }

    /**
     * 获得词库关键词
     * @return
     */
    public List<String> getSynonyms() {
        if(!PropertyUtil.isLoadFromDb()){
            return null;
        }

        List<String> synonyms = new ArrayList<String>();
        Connection conn = JdbcUtil.getSingleton().getConn();
        String sql = "select keyword,synonymword from search_synonym_t";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //add original words and synonym words
                synonyms.add(rs.getString(1));
                synonyms.add(rs.getString(2));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return synonyms;
    }

    /**
     * 获得词库关键词
     * @return
     */
    public List<String> getSearchAttributes() {
        if(!PropertyUtil.isLoadFromDb()){
            return null;
        }

        List<String> searchAttributeList = new ArrayList<String>();
        Connection conn = JdbcUtil.getSingleton().getConn();
        String sql = "select attrval from search_attr_val_t";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //add original words and synonym words
                searchAttributeList.add(rs.getString(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
        }
        return searchAttributeList;
    }
}
