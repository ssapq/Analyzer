package com.vmall.search.analyzer.solr;

import com.vmall.search.analyzer.thread.ThreadManager;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import com.vmall.search.analyzer.lucene.IKTokenizer;

import java.util.Map;

/**
 * Created by ss on 2016/8/14.
 */
public class IKTokenizerFactory  extends TokenizerFactory {
    private static Logger logger = Logger.getLogger(IKTokenizerFactory.class);

    private boolean useSmart;
    private boolean useFragment;

    public boolean useSmart() {
        return useSmart;
    }

    public void setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
    }

    public boolean isUseSmart() {
        return useSmart;
    }

    public boolean isUseFragment() {
        return useFragment;
    }

    public void setUseFragment(boolean useFragment) {
        this.useFragment = useFragment;
    }

    /* 线程内共享 */
    private ThreadLocal<IKTokenizer> tokenizerLocal = new ThreadLocal<IKTokenizer>();

    public IKTokenizerFactory(Map<String, String> args){
        super(args);
        if(args.get("useSmart") != null ){
            this.useSmart = Boolean.valueOf(args.get("useSmart"));
        }

        if(args.get("useFragment") != null ){
            this.useFragment = Boolean.valueOf(args.get("useFragment"));
        }

        logger.info("starting monitor");
        ThreadManager.work();

    }

    @Override
    public Tokenizer create(AttributeFactory factory){
        IKTokenizer tokenizer = tokenizerLocal.get();
        if(tokenizer == null) {
            tokenizer = newTokenizer();
        }

        if(logger.isDebugEnabled()){
            logger.debug("--useSmart is: " + useSmart +  " ; --useFragment is: " + useFragment + " ;--create tokenizer: " + tokenizer.toString());
        }

        return tokenizer;
    }

    /**
     * 创建分词器
     * @return
     */
    private IKTokenizer newTokenizer(){
        IKTokenizer tokenizer = new IKTokenizer(useSmart,useFragment);
        tokenizerLocal.set(tokenizer);
        return tokenizer;
    }
}
