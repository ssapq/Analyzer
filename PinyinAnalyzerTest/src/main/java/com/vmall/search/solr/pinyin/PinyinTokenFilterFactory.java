package com.vmall.search.solr.pinyin;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.util.TokenFilterFactory;

import java.util.Map;

/**
 * Created by ss on 2016/11/12.
 */
public class PinyinTokenFilterFactory  extends TokenFilterFactory {

    public PinyinTokenFilterFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public TokenFilter create(TokenStream input) {
        return new PinyinTokenFilter(input);
    }
}
