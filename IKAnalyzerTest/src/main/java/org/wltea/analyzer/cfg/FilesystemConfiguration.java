package org.wltea.analyzer.cfg;

import java.util.ArrayList;
import java.util.List;


public class FilesystemConfiguration implements Configuration {
	private static Configuration instance = null;  
    private FilesystemConfiguration() {  
       // Exists only to defeat instantiation.  
    }  
    public static Configuration getInstance() {  
       if (instance == null) {  
    	   instance = new FilesystemConfiguration();  
       }  
       return instance;  
    }  
	
    

	/*
	 * 分词器默认字典路径 
	 */
	private static final String PATH_DIC_MAIN = "conf/main2012.dic";
	private static final String PATH_DIC_QUANTIFIER = "conf/quantifier.dic";
	private static final String PATH_EXT_DIC = "conf/ext.dic";
	private static final String PATH_EXT_DIC_STOP="conf/stopword.dic";

//	private static final String PATH_DIC_MAIN = "D:/develop/workspace/analyzer-ik/src/main/resources/conf/main2012.dic";
//	private static final String PATH_DIC_QUANTIFIER = "D:/develop/workspace/analyzer-ik/src/main/resources/conf/quantifier.dic";
//	private static final String PATH_EXT_DIC = "D:/develop/workspace/analyzer-ik/src/main/resources/conf/ext.dic";
//	private static final String PATH_EXT_DIC_STOP="D:/develop/workspace/analyzer-ik/src/main/resources/conf/stopword.dic";
	private boolean useSmart;
	@Override
	public boolean useSmart() {
		return useSmart;
	}

	@Override
	public void setUseSmart(boolean useSmart) {
		this.useSmart = useSmart;

	}

	@Override
	public String getMainDictionary() {
		return PATH_DIC_MAIN;
	}

	@Override
	public String getQuantifierDicionary() {
		return PATH_DIC_QUANTIFIER;
	}

	@Override
	public List<String> getExtDictionarys() {
		List<String> extDictFiles = new ArrayList<String>(2);
		extDictFiles.add(PATH_EXT_DIC);
		return extDictFiles;
	}

	@Override
	public List<String> getExtStopWordDictionarys() {
		List<String> stopDictFiles = new ArrayList<String>(2);
		stopDictFiles.add(PATH_EXT_DIC_STOP);
		return stopDictFiles;
	}

}
