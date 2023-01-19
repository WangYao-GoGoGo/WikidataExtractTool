package com.wikidata.extractor.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;	
    }
	
	public static void main(String[] args) {
		String replaceBlank = replaceBlank("ighborhood of Korangi Town in Karachi, Sindh, Pakistan. This neighborhood is named after the Sufi poet Shah");
		System.out.println(replaceBlank);
	}
}
