package com.vmall.analyzer.synonym.solr;

import com.vmall.analyzer.synonym.job.JobBuilder;
import com.vmall.analyzer.synonym.thread.ThreadManager;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

/**
 * Created by shaosh on 2016/8/17.
 */
public class SynonymFilterFactory  extends TokenFilterFactory {
    private static Logger logger = Logger.getLogger(SynonymFilterFactory.class);
    public SynonymFilterFactory(Map<String, String> args) {
        super(args);

        logger.info("starting ses synonym analyzer monitor");
        ThreadManager.work();
    }

    @Override
    public TokenFilter create(TokenStream input) {
        return new SynonymFilter(input);
    }


}
