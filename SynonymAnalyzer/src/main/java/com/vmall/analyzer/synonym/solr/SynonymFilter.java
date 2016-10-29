package com.vmall.analyzer.synonym.solr;

import com.vmall.analyzer.synonym.core.SynonymWordsDictionary;
import com.vmall.analyzer.synonym.loader.DynamicSynonymWordsLoader;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import java.io.IOException;
import java.util.*;

/**
 * Created by shaosh on 2016/8/16.
 */
public class SynonymFilter extends TokenFilter {

    private State current;
    private Stack<String> synonymStack;
    private final TypeAttribute typeAttribute = addAttribute(TypeAttribute.class);
    private final CharTermAttribute charTermAttribute;
    private final PositionIncrementAttribute positionIncrementAttribute;

    public SynonymFilter(TokenStream input) {
        super(input);
        synonymStack = new Stack<String>();
        this.charTermAttribute = addAttribute(CharTermAttribute.class);
        this.positionIncrementAttribute = addAttribute(PositionIncrementAttribute.class);
    }

    @Override
    final public boolean incrementToken() throws IOException {
        if(synonymStack.size() > 0){
            restoreState(current);
//            charTermAttribute.setEmpty();
//            charTermAttribute.append(synonymStack.pop());
            String synonymWord = synonymStack.pop();
            this.charTermAttribute.copyBuffer(synonymWord.toCharArray(), 0, synonymWord.length());
            typeAttribute.setType("SYNONYM");
            positionIncrementAttribute.setPositionIncrement(0);
            return true;
        }

        if(!input.incrementToken()){
            return false;
        }


        if(this.fillSynonymWordStack()){
            current = captureState();
        }

        return true;
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

    private boolean fillSynonymWordStack(){
        this.synonymWordEngine(charTermAttribute.toString());
        return true;
    }

    private void synonymWordEngine(String term){
        List<String> resultList = null;
        try {

            if(!SynonymWordsDictionary.getSingleton().isSynonyWordsLoadFinieshed()){
                DynamicSynonymWordsLoader.getSingleton().loadSynonymWords();
            }

            resultList = SynonymWordsDictionary.getSingleton().getWordList(term.trim().toLowerCase());
            if (resultList == null || resultList.isEmpty()) {
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        for(String synonyWord : resultList){
            synonymStack.push(synonyWord);
        }
        return;
    }
}
