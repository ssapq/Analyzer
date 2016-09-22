package org.wltea.analyzer.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by shaosh on 2016/9/18.
 */
public class PropertyUtil {

    /**@function: get property value by property name
     * @param: String property name
     * @return: String property value
     */
    public static String getPropertyValue(String configFileName,String propertyName) {
        if(configFileName == null || configFileName.isEmpty()){

        }

        if(propertyName == null || propertyName.isEmpty()){

        }

        String propertyValue = null;

        Properties prop = new Properties();//属性集合对象
        InputStream in = null;
        try {
//             in = new BufferedInputStream(new FileInputStream(configFileName));
            in = ClassLoader.getSystemClassLoader().getResourceAsStream(configFileName);
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
