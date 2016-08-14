package org.wltea.analyzer.solr;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

import java.util.Map;

/**
 * Created by ss on 2016/8/14.
 */
public class IKTokenizerFactory  extends TokenizerFactory {

    protected IKTokenizerFactory(Map<String, String> args) {
        super(args);
    }

    @Override
    public Tokenizer create(AttributeFactory factory) {
        return null;
    }
}
