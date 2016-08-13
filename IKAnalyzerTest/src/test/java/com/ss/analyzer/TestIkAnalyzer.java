package com.ss.analyzer;

import junit.framework.TestCase;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit test for simple App.
 */
public class TestIkAnalyzer extends TestCase{

    public TestIkAnalyzer() {
        String indexDir = "";
        Document document = new Document();
        try {
            Analyzer analyzer = new IKAnalyzer();
//            Directory directory = new RAMDirectory();
            Path docDir = Paths.get(indexDir);
            Directory directory = FSDirectory.open(Paths.get(indexDir));
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter iwriter = new IndexWriter(directory, config);
            iwriter.addDocument(document);
            iwriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
