package org.wltea.analyzer.util;

import com.mysql.jdbc.Connection;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.wltea.analyzer.cfg.JdbcConfig;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by shaosh on 2016/9/18.
 */
public class JdbcUtil {
    private static Logger logger = Logger.getLogger(JdbcUtil.class);

    private static String driver;
    private static String url;
    private static String password;
    private static String username;

    private static JdbcUtil singleton;

    static {
        singleton = new JdbcUtil();
    }

    private JdbcUtil(){
        JdbcConfig jdbcConfig = PropertyUtil.getDbConfig();
        driver = jdbcConfig.getDriver();
        url = jdbcConfig.getUrl();
        try{
            String key = PropertyUtil.getStrEnDeCryptKey().substring(0, 16);
            if(StringUtils.isBlank(key)){
                throw new Exception("key is null");
            }
            username = EncryptUtils.aesDecrypt(jdbcConfig.getUsername(), key);
            password = EncryptUtils.aesDecrypt(jdbcConfig.getPassword(), key);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }
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
