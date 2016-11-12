package org.wltea.analyzer.solr;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.lucene.EmptyTokenizer;

import java.util.Map;

/**
 * Created by shaosh on 2016/11/3.
 */
public class EmptyTokenizerFactory extends TokenizerFactory {


    /* 线程内共享 */
    private ThreadLocal<EmptyTokenizer> tokenizerLocal = new ThreadLocal<EmptyTokenizer>();

    public EmptyTokenizerFactory(Map<String, String> args){
        super(args);
    }

    @Override
    public Tokenizer create(AttributeFactory factory){
        System.out.println("创建分词器");
        EmptyTokenizer tokenizer = tokenizerLocal.get();
        if(tokenizer == null) {
            tokenizer = newTokenizer();
        }
        return tokenizer;
    }

    /**
     * 创建分词器
     * @return
     */
    private EmptyTokenizer newTokenizer(){
        EmptyTokenizer emptyTokenizer = new EmptyTokenizer();
        tokenizerLocal.set(emptyTokenizer);
        return emptyTokenizer;
    }
}
