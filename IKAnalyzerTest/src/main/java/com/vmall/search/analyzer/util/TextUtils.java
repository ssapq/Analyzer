package com.vmall.search.analyzer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaosh on 2016/11/16.
 */
public class TextUtils {

    /**
     * 细分词汇
     * @return
     * @throws Exception
     */
    public static List<String> splitWord(String word) throws Exception{
        if(word == null || word.isEmpty()){
            return null;
        }

        List<String> result = new ArrayList<String>();
        for(char ch : word.toCharArray()){
            if(isChinese(ch)){
                result.add(String.valueOf(ch));
            }
        }
        return result;
    }

    /**
     * 判断字符串中是否含有中文
     *
     * @param
     * @return
     */
    public static int chineseCharCount(String s) {
        int count = 0;
        if ((null == s) || ("".equals(s.trim())))
            return count;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i)))
                count++;
        }
        return count;
    }

    /**
     * 判断字符是否是中文
     *
     * @param a
     * @return
     */
    public static boolean isChinese(char a) {
        int v = a;
        return (v >= 19968) && (v <= 171941);
    }
}
