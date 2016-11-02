package org.wltea.analyzer.lucene;

import org.apache.lucene.analysis.Tokenizer;

import java.io.IOException;

/**
 * Created by ss on 2016/11/2.
 */
public class EmptyTokenizer extends Tokenizer {
    @Override
    public boolean incrementToken() throws IOException {

        return true;
    }
}
