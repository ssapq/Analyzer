package org.wltea.analyzer.util;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by shaosh on 2016/9/18.
 */
public class JdbcUtil {

    private static String driver;
    private static String url;
    private static String password;
    private static String username;

    private static JdbcUtil singleton;

    static {
        singleton = new JdbcUtil();
    }

    private JdbcUtil(){
//        driver = PropertyUtil.getPropertyValue(Constant.JDBC_CONFIG,Constant.JDBC_DRIVER);
//        url = PropertyUtil.getPropertyValue(Constant.JDBC_CONFIG,Constant.JDBC_URL);
//        password = PropertyUtil.getPropertyValue(Constant.JDBC_CONFIG,Constant.JDBC_PASSWORD);
//        username = PropertyUtil.getPropertyValue(Constant.JDBC_CONFIG,Constant.JDBC_USERNAME);

        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://15.114.108.70:3306/search?useUnicode=true&characterEncoding=utf8";
        username = "root";
        password = "root";
    }

    public static JdbcUtil getSingleton() {
        synchronized(JdbcUtil.class){
            if(singleton == null){
                singleton = new JdbcUtil();
                return singleton;
            }else{
                return singleton;
            }
        }
    }

    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}