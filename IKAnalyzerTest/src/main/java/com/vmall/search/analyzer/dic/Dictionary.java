/**
 * IK 中文分词  版本 5.0
 * IK Analyzer release 5.0
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 * 
 * 
 */
package com.vmall.search.analyzer.dic;

import org.apache.log4j.Logger;

/**
 * 词典管理类,单子模式
 */
public class Dictionary {
	private static Logger logger = Logger.getLogger(Dictionary.class);

	private boolean useFrament;

	public boolean isUseFrament() {
		return useFrament;
	}

	public void setUseFrament(boolean useFrament) {
		this.useFrament = useFrament;
	}


	public Dictionary(boolean useFrament){
		this.useFrament = useFrament;
	}

	/**
	 * 检索匹配主词典
	 * @param charArray
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInMainDict(char[] charArray){
		if(useFrament){
			return DictionaryCore.getSingleton().get_FragmentMainDict().match(charArray);
		}else{
			return DictionaryCore.getSingleton().get_NoFragmentMainDict().match(charArray);
		}
	}
	
	/**
	 * 检索匹配主词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInMainDict(char[] charArray , int begin, int length){
		if(useFrament){
			return DictionaryCore.getSingleton().get_FragmentMainDict().match(charArray, begin, length);
		}else{
			return DictionaryCore.getSingleton().get_NoFragmentMainDict().match(charArray, begin, length);
		}
	}
	
	/**
	 * 检索匹配量词词典
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return Hit 匹配结果描述
	 */
	public Hit matchInQuantifierDict(char[] charArray , int begin, int length){
		return DictionaryCore.getSingleton().get_QuantifierDict().match(charArray, begin, length);
	}
	
	
	/**
	 * 从已匹配的Hit中直接取出DictSegment，继续向下匹配
	 * @param charArray
	 * @param currentIndex
	 * @param matchedHit
	 * @return Hit
	 */
	public Hit matchWithHit(char[] charArray , int currentIndex , Hit matchedHit){
		DictSegment ds = matchedHit.getMatchedDictSegment();
		return ds.match(charArray, currentIndex, 1 , matchedHit);
	}
	
	
	/**
	 * 判断是否是停止词
	 * @param charArray
	 * @param begin
	 * @param length
	 * @return boolean
	 */
	public boolean isStopWord(char[] charArray , int begin, int length){			
		return DictionaryCore.getSingleton().get_StopWordDict().match(charArray, begin, length).isMatch();
	}	

	
}
