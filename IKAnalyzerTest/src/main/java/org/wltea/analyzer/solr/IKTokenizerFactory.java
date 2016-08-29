package org.wltea.analyzer.solr;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ss on 2016/8/14.
 */
public class IKTokenizerFactory  extends TokenizerFactory {
    private boolean useSmart;

    public boolean useSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }


    /* 线程内共享 */
    private ThreadLocal<IKTokenizer> tokenizerLocal = new ThreadLocal<IKTokenizer>();

    public IKTokenizerFactory(Map<String, String> args){
        super(args);
    }

    @Override
    public Tokenizer create(AttributeFactory factory){
        System.out.println("创建分词器");
        IKTokenizer tokenizer = tokenizerLocal.get();
        if(tokenizer == null) {
            tokenizer = newTokenizer();
        }
        return tokenizer;
    }

    /**
     * 创建分词器
     * @return
     */
    private IKTokenizer newTokenizer(){
        IKTokenizer tokenizer = new IKTokenizer(this.useSmart);
        tokenizerLocal.set(tokenizer);
        return tokenizer;
    }
}
