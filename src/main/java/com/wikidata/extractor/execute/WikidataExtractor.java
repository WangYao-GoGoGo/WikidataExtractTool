package com.wikidata.extractor.execute;

import com.wikidata.extractor.utils.JsonUtils;

public class WikidataExtractor {
	
	 public static void main(String[] args) throws  Exception{
		int poolsize = Runtime.getRuntime().availableProcessors();
		System.out.println(poolsize);
//		String path = WikidataExtractor.class.getClassLoader().getResource("src/main/resources/wikidata_train/en_train.json").getPath();
//		String s = JsonUtils.readJsonFile("src/main/resources/wikidata_train/en_train.jsonl");
		long start = System.currentTimeMillis();
        JsonUtils jsonUtils = new JsonUtils();
        // file count: 2754389
        String jsonFilePath = "src/main/resources/wikidata_train/en_train.jsonl";
//        System.out.println(jsonUtils.getFileLineNum(jsonFilePath));
        jsonUtils.readJsonFile4(jsonFilePath);
//        jsonUtils.parse(jsonFilePath);
        System.out.println("Total Time Taken : "+(System.currentTimeMillis() - start)/1000 + " secs");
    }
	 

}
