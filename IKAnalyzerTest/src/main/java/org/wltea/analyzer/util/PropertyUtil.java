package org.wltea.analyzer.util;

import org.wltea.analyzer.cfg.JdbcConfig;

import java.io.*;
import java.util.Properties;

/**
 * Created by shaosh on 2016/9/18.
 */
public class PropertyUtil {

    private PropertyUtil(){

    }

    private static Class getThisClass(){
        PropertyUtil propertyUtil = new PropertyUtil();
        Class clazz = propertyUtil.getClass();
        return clazz;
    }

    public static JdbcConfig getDbConfig(){
        JdbcConfig jdbcConfig = new JdbcConfig();
        jdbcConfig.setDriver(getPropertyValue("dictionary.properties","jdbc.driver"));
        jdbcConfig.setUrl(getPropertyValue("dictionary.properties","jdbc.url"));
        jdbcConfig.setPassword(getPropertyValue("dictionary.properties","jdbc.password"));
        jdbcConfig.setUsername(getPropertyValue("dictionary.properties","jdbc.user"));
        return jdbcConfig;
    }

    public static String getStrEnDeCryptKey(){
        return getPropertyValue("dictionary.properties","strEnDeCryptKey");
    }

    public static String getCronExpression(){
        return getPropertyValue("dictionary.properties","job.cronexpression");
    }

    public static boolean isLoadFromDb(){
        return Boolean.parseBoolean(getPropertyValue("dictionary.properties","init.loadfromdb"));
    }

    /**@function: get property value by property name
     * @param: String property name
     * @return: String property value
     */
    private static String getPropertyValue(String configFileName,String propertyName) {
        if(configFileName == null || configFileName.isEmpty()){

        }

        if(propertyName == null || propertyName.isEmpty()){

        }

        String propertyValue = null;

        Properties prop = new Properties();//属性集合对象
        InputStream in = null;
        try {
//             in = new BufferedInputStream(new FileInputStream(configFileName));
            in = getThisClass().getClassLoader().getResourceAsStream(configFileName);
//            in = ClassLoader.getSystemClassLoader().getResourceAsStream(configFileName);
            prop.load(in);
            in.close();

            return (String)prop.get(propertyName);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }

        return propertyValue;
    }
}
