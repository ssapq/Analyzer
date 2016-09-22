package com.vmall.analyzer.synonym.db;

import com.mysql.jdbc.Connection;
import com.vmall.analyzer.synonym.core.Word;
import com.vmall.analyzer.synonym.util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shaosh on 2016/9/20.
 */
public class SynonymwordDBDao {

    /**
     *
     * @return
     */
    public List<Word> getKeywords() {
        List<Word> keywordList = new ArrayList<Word>();

        Connection conn = JdbcUtil.getSingleton().getConn();
        String sql = "select synonymword,keyword from search_synonym_t";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Word word = new Word();
                word.setOriginalWord(rs.getString(1));
                word.setSynonyWord(rs.getString(2));
                keywordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keywordList;
    }
}
