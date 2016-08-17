package org.wltea.analyzer.solr;

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
        return new SynonymFilter(input);
    }


}
