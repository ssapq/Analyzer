package org.wltea.analyzer.db;

import com.mysql.jdbc.Connection;
import org.wltea.analyzer.util.JdbcUtil;

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
     *
     * @return
     */
    public List<String> getKeywords() {
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
}
