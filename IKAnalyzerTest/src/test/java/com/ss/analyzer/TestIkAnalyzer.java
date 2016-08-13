package com.ss.analyzer;

import junit.framework.TestCase;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit test for simple App.
 */
public class TestIkAnalyzer extends TestCase{

    public TestIkAnalyzer() {

    }


    public void tests() {
        String indexDir = "E:/temp";
        String content = "8月13日电 (记者 张添福)青海省地震局13日下午消息，13日16时29分，发生在青海省海北州门源县的M4.6级地震，震中位于门源县城北偏西约36公里处的北山乡夏季牧场，距离西宁市120公里，本次地震为今年“1·21”门源地震的强余震。";

        Document document = new Document();
        Field pathField = new TextField("content", content.toString(), Field.Store.YES);
        document.add(pathField);
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
