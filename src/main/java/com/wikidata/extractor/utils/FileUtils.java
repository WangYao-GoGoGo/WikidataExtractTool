package com.wikidata.extractor.utils;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class FileUtils {
	 
	 public static int getFileLineNum(String filePath) {
	        try (LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(filePath))){
	            lineNumberReader.skip(Long.MAX_VALUE);
	            int lineNumber = lineNumberReader.getLineNumber();
	            return lineNumber + 1;
	        } catch (IOException e) {
	            return -1;
	        }
	    }
}
