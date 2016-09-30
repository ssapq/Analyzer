package com.vmall.analyzer.synonym.solr;

import com.vmall.analyzer.synonym.job.JobBuilder;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

/**
 * Created by shaosh on 2016/8/17.
 */
public class SynonymFilterFactory  extends TokenFilterFactory {
    public SynonymFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public TokenFilter create(TokenStream input) {
        JobBuilder.getSingleton().startJob();
        return new SynonymFilter(input);
    }


}
