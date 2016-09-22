package com.vmall.analyzer.synonym;

import junit.framework.TestCase;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.dic.DynmicDictinoaryLoader;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit test for simple App.
 */
public class TestIkAnalyzer extends TestCase{

    public TestIkAnalyzer() {

    }

    public void tests() {
        String indexDir = "C:\\my files\\temp";
       /* String content = "8月13日电 (记者 张添福)青海省地震局13日下午消息，13日16时29分，" +
                "发生在青海省海北州门源县的M4.6级地震，*测试*震中位于门源县城北偏西约36公里处的北山乡夏季牧场，" +
                "距离西宁市120公里，本次地震为今年“1·21”门源地震的强余震。";*/
        String content = "测试词库1绯闻绯闻绯闻";
        Document document = new Document();
        Field pathField = new TextField("content", content.toString(), Field.Store.YES);
        document.add(pathField);

        Analyzer analyzer = new IKAnalyzer(true);

        Path docDir = Paths.get(indexDir);
        Directory directory = null;

        try {
//            Directory directory = new RAMDirectory();

            directory = FSDirectory.open(Paths.get(indexDir));
            this.index(analyzer,directory,document);
            this.search(directory,analyzer);
            System.out.println("========================1===========================");
//
//            content = "钢化膜手机膜3";
//            document = new Document();
//            Field pathField3 = new TextField("content", content.toString(), Field.Store.YES);
//            document.add(pathField3);
//
//            DynmicDictinoaryLoader.getSingleton().doAddWord("动态加载词钢化膜");
//            this.index(analyzer,directory,document);
//            this.search(directory,analyzer);
//            System.out.println("========================2===========================");
//
//            content = "钢化膜手机膜4";
//            document = new Document();
//            Field pathField4 = new TextField("content", content.toString(), Field.Store.YES);
//            document.add(pathField4);
//
//            DynmicDictinoaryLoader.getSingleton().doDeleteWord("动态加载词钢化膜");
//            this.index(analyzer,directory,document);
//            this.search(directory,analyzer);
//            System.out.println("========================3===========================");
//
//            content = "钢化膜手机膜5";
//            document = new Document();
//            Field pathField5 = new TextField("content", content.toString(), Field.Store.YES);
//            document.add(pathField5);
//
//            DynmicDictinoaryLoader.getSingleton().doAddWord("动态加载词钢化膜");
//            this.index(analyzer,directory,document);
//            this.search(directory,analyzer);
//            System.out.println("========================4===========================");
//
//
//            content = "钢化膜手机膜6";
//            document = new Document();
//            Field pathField6 = new TextField("content", content.toString(), Field.Store.YES);
//            document.add(pathField6);
//
//            DynmicDictinoaryLoader.getSingleton().doAddWord("动态加载词钢化膜");
//            this.index(analyzer,directory,document);
//            this.search(directory,analyzer);
//            System.out.println("========================5===========================");
//
//
//            content = "钢化膜手机膜7";
//            document = new Document();
//            Field pathField7 = new TextField("content", content.toString(), Field.Store.YES);
//            document.add(pathField7);
//
//            DynmicDictinoaryLoader.getSingleton().doModifyWord("动态加载词钢化膜","动态加载词钢化膜哦");
//            this.index(analyzer,directory,document);
//            this.search(directory,analyzer);
//            System.out.println("========================6===========================");

            directory.close();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    private void index(Analyzer analyzer, Directory directory,Document document ){

        try {
//            Directory directory = new RAMDirectory();

            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter iwriter = new IndexWriter(directory, config);
            iwriter.addDocument(document);
            iwriter.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void search(Directory directory,Analyzer analyzer) throws Exception{
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
//        QueryParser parser = new QueryParser("content", analyzer);
        Term term = new Term("content", "测试同义词1");
        Query query = new TermQuery(term);
//        Query query = parser.parse("\"\"");
        ScoreDoc[] hits = isearcher.search(query, null, 1000).scoreDocs;
        // Iterate through the results:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            System.out.println(hitDoc.get("content"));
        }
        ireader.close();
    }

}
