package org.wltea.analyzer.db;

import com.mysql.jdbc.Connection;
import org.wltea.analyzer.util.JdbcUtil;
import org.wltea.analyzer.util.PropertyUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaosh on 2016/9/20.
 */
public class KeywordDBDao {

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
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
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

        List<String> synonyms = new ArrayList<String>();

        Connection conn = JdbcUtil.getSingleton().getConn();
        String sql = "select attrval from search_attr_val_t";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //add original words and synonym words
                synonyms.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return synonyms;
    }
}
