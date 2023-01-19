package com.wikidata.extractor.execute;

import com.wikidata.extractor.utils.FileUtils;

public class WikidataTest {
	
	public static void main(String[] args) {
		computeFileLineCount();
	}
	
	/**
	  * comput total count of the lines of all files
	  */
	 public static void computeFileLineCount() {
		 int totalCount = 0;
		 FileUtils fileUtils = new FileUtils();
		 for (int i = 0; i < 28; i++) {
			 String jsonFilePath = "src/main/resources/wikidata_train/docInfo_train_" + i + ".txt";
			 int fileLineNum = fileUtils.getFileLineNum(jsonFilePath);
			 totalCount += fileLineNum;
		}
		 System.out.println("totalCount:" + totalCount);
		 
	 }
	  
}
