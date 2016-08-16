package org.wltea.analyzer.solr;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import java.io.IOException;

/**
 * Created by shaosh on 2016/8/16.
 */
public class SynonymFilter extends TokenFilter {

    public SynonymFilter(TokenStream input) {
        super(input);
    }

    @Override
    public boolean incrementToken() throws IOException {
        restoreState(null);
        return false;
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
}
