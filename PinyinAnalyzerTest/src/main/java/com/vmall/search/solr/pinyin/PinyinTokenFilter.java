package com.vmall.search.solr.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import java.io.IOException;
import java.util.*;

/**
 * Created by ss on 2016/11/12.
 */
public class PinyinTokenFilter  extends TokenFilter {
    private static Logger logger = Logger.getLogger(PinyinTokenFilter.class);

    private State current;
    private Stack<String> pinyinStack;
    private final TypeAttribute typeAttribute = addAttribute(TypeAttribute.class);
    private final CharTermAttribute charTermAttribute;
    private final PositionIncrementAttribute positionIncrementAttribute;

    public PinyinTokenFilter(TokenStream input) {
        super(input);
        pinyinStack = new Stack<String>();
        this.charTermAttribute = addAttribute(CharTermAttribute.class);
        this.positionIncrementAttribute = addAttribute(PositionIncrementAttribute.class);
        this.pinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        this.pinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    @Override
    final public boolean incrementToken() throws IOException {
        if(pinyinStack.size() > 0){
            restoreState(current);
            String pinyin = pinyinStack.pop();
            this.charTermAttribute.copyBuffer(pinyin.toCharArray(), 0, pinyin.length());
            typeAttribute.setType("PINYIN");
            positionIncrementAttribute.setPositionIncrement(0);
            return true;
        }

        if(!input.incrementToken()){
            return false;
        }


        if(this.fillSynonymWordStack()){
            current = captureState();
        }

        return true;
    }

    @Override
    public void end() throws IOException {
        super.end();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public void reset() throws IOException {
        super.reset();
    }

    private boolean fillSynonymWordStack(){
        this.pinyinEngine(charTermAttribute.toString());
        return true;
    }

    private void pinyinEngine(String term){
        List<String> resultList = new ArrayList<String>();
        try {
            if(this.getPyShort(term) != null && !this.getPyShort(term).isEmpty()){
                resultList.addAll(this.getPyShort(term));
            }
            if(this.getPyString(term) != null && !this.getPyString(term).isEmpty()){
                resultList.addAll(this.getPyString(term));
            }
        }catch (Exception e){
            logger.info(e.getMessage(),e);
        }

        for(String synonyWord : resultList){
            pinyinStack.push(synonyWord);
        }
        return;
    }

    private HanyuPinyinOutputFormat pinyinOutputFormat = new HanyuPinyinOutputFormat(); // 拼音转接输出格式

    /**
     * 获取拼音缩写
     *
     * @param chinese
     *            含中文的字符串，若不含中文，原样输出
     * @return 转换后的文本
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    private Collection<String> getPyShort(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        List<String[]> pinyinList = new ArrayList<String[]>();
        for (int i = 0; i < chinese.length(); i++) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(chinese.charAt(i), this.pinyinOutputFormat);
            if (pinyinArray != null && pinyinArray.length > 0) {
                pinyinList.add(pinyinArray);
            }
        }

        Set<String> pinyins = null;
        for (String[] array : pinyinList) {
            if (pinyins == null || pinyins.isEmpty()) {
                pinyins = new HashSet<String>();

                for (String charPinpin : array) {
                    pinyins.add(charPinpin.substring(0, 1));
                }
            } else {
                Set<String> pres = pinyins;
                pinyins = new HashSet<String>();
                for (String pre : pres) {
                    for (String charPinyin : array) {
                        pinyins.add(pre + charPinyin.substring(0, 1));
                    }
                }
            }
        }
        return pinyins;
    }

    /**
     * 获取拼音
     *
     * @param chinese
     *            含中文的字符串，若不含中文，原样输出
     * @return 转换后的文本
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    private Collection<String> getPyString(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        List<String[]> pinyinList = new ArrayList<String[]>();
        for (int i = 0; i < chinese.length(); i++) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(chinese.charAt(i), this.pinyinOutputFormat);
            if (pinyinArray != null && pinyinArray.length > 0) {
                pinyinList.add(pinyinArray);
            }
        }
        Set<String> pinyins = null;
        for (String[] array : pinyinList) {
            if (pinyins == null || pinyins.isEmpty()) {
                pinyins = new HashSet<String>();
                for (String charPinpin : array) {
                    pinyins.add(charPinpin);
                }
            } else {
                Set<String> pres = pinyins;
                pinyins = new HashSet<String>();
                for (String pre : pres) {
                    for (String charPinyin : array) {
                        pinyins.add(pre + charPinyin);
                    }
                }
            }
        }
        return pinyins;
    }
}
