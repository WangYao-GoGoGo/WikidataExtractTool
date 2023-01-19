package com.wikidata.extractor.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.wikidata.extractor.model.DocInfo;
import com.wikidata.extractor.service.WikidataService;
import com.wikidata.process.thread.BasicThreadPool;
import com.wikidata.process.thread.ThreadPool;

public class JsonUtils {
	
	private WikidataService wikidataService = new WikidataService();
	
	final ThreadPool threadPool = new BasicThreadPool(8, 6, 4, 1000);

    public void readWithFastJson(String filePath) throws FileNotFoundException {
//        String jsonString = "{\"array\":[1,2,3],\"arraylist\":[{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"}],\"object\":{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},\"string\":\"HelloWorld\"}";
        // 为了直观，方便运行，就用StringReader做示例！
        InputStreamReader inputStreamReader = new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8);
        JSONReader reader = new JSONReader(inputStreamReader);
        reader.startObject();
        System.out.println("start fastjson");
        String docKey = reader.readString();
        if(docKey.equals("doc")) {
        	reader.startArray();
        	while (reader.hasNext()) {
        		String key = reader.readString();
        		System.out.println("key " + key);
        		
        		List<DocInfo> docInfos = JSON.parseArray(key, DocInfo.class);
        		System.out.println(docInfos);
        		System.out.println(docInfos);
        		
//        		if (key.equals("entities")) { // arrayList
//        			reader.startArray();
//        			System.out.println("start " + key);
//        			while (reader.hasNext()) {
//        				reader.startObject();
//        				System.out.println("start arraylist item");
//        				while (reader.hasNext()) {
//        					String arrayListItemKey = reader.readString();
//        					String arrayListItemValue = reader.readObject().toString();
//        					System.out.println("key " + arrayListItemKey);
//        					System.out.println("value " + arrayListItemValue);
//        				}
//        				reader.endObject();
//        				System.out.println("end arraylist item");
//        			}
//        			reader.endArray();
//        			System.out.println("end " + key);
//        		} else if (key.equals("triples")) { // arrayList
//        			reader.startArray();
//        			System.out.println("start " + key);
//        			while (reader.hasNext()) {
//        				reader.startObject();
//        				System.out.println("start arraylist item");
//        				while (reader.hasNext()) {
//        					String arrayListItemKey = reader.readString();
//        					if (arrayListItemKey.equals("subject")) {
//        						reader.startObject();
//        						System.out.print("start object item");
//        						while (reader.hasNext())
//        						{
//        							String objectKey = reader.readString();
//        							String objectValue = reader.readObject().toString();
//        							System.out.print("key " + objectKey);
//        							System.out.print("value " + objectValue);
//        						}
//        						reader.endObject();
//        						System.out.print("end object item");
//        					} else if (arrayListItemKey.equals("predicate")) {
//        						reader.startObject();
//        						System.out.print("start object item");
//        						while (reader.hasNext())
//        						{
//        							String objectKey = reader.readString();
//        							Object readObject = reader.readObject();
//        							if(readObject != null) {
//        								String objectValue = readObject.toString();
//        								System.out.print("key " + objectKey);
//        								System.out.print("value " + objectValue);
//        							}
//        						}
//        						reader.endObject();
//        						System.out.print("end object item");
//        					} else if (arrayListItemKey.equals("object")) {
//        						reader.startObject();
//        						System.out.print("start object item");
//        						while (reader.hasNext())
//        						{
//        							String objectKey = reader.readString();
//        							String objectValue = reader.readObject().toString();
//        							System.out.print("key " + objectKey);
//        							System.out.print("value " + objectValue);
//        						}
//        						reader.endObject();
//        						System.out.print("end object item");
//        					} else if (arrayListItemKey.equals("sentence_id")) {
//        						System.out.println("start string");
//        						Object readObject = reader.readObject();
//        						if(readObject != null) {
//        							String value = readObject.toString();
//        							System.out.println("sentence_id " + value);
//        							System.out.println("end string");
//        						}
//        					} else if (arrayListItemKey.equals("dependency_path")) {
//        						System.out.println("start string");
//        						Object readObject = reader.readObject();
//        						if(readObject != null) {
//        							String value = readObject.toString();
//        							System.out.println("title " + value);
//        							System.out.println("end string");
//        						}
//        					} else if (arrayListItemKey.equals("confidence")) {
//        						System.out.println("start string");
//        						Object readObject = reader.readObject();
//        						if(readObject != null) {
//        							String value = readObject.toString();
//        							System.out.println("confidence " + value);
//        							System.out.println("end string");
//        						}
//        					} else if (arrayListItemKey.equals("annotator")) {
//        						System.out.println("start string");
//        						Object readObject = reader.readObject();
//        						if(readObject != null) {
//        							String value = readObject.toString();
//        							System.out.println("annotator " + value);
//        							System.out.println("end string");
//        						}
//        					}
//        				}
//        				reader.endObject();
//        				System.out.println("end arraylist item");
//        			}
//        			reader.endArray();
//        			System.out.println("end " + key);
//        		} else if (key.equals("docid")) {
//        			System.out.println("start string");
//        			String value = reader.readObject().toString();
//        			System.out.println("docid " + value);
//        			System.out.println("end string");
//        		} else if (key.equals("title")) {
//        			System.out.println("start string");
//        			String value = reader.readObject().toString();
//        			System.out.println("title " + value);
//        			System.out.println("end string");
//        		} else if (key.equals("uri")) {
//        			System.out.println("start string");
//        			String value = reader.readObject().toString();
//        			System.out.println("uri " + value);
//        			System.out.println("end string");
//        		} else if (key.equals("text")) {
//        			System.out.println("start string");
//        			String value = reader.readObject().toString();
//        			System.out.println("text " + value);
//        			System.out.println("end string");
//        		}
        	}
        	reader.endArray();
        }
        reader.endObject();
        System.out.println("end fastjson");
    }
	
//    public static void readJsonFile2(String filePath) throws Exception{
//        BufferedReader reader = null;
//        try {
//            FileInputStream fileInputStream = new FileInputStream(filePath);
//            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
//            reader = new BufferedReader(inputStreamReader);
//            String tempString = null;
//            List<DocInfo> docInfos = new ArrayList<DocInfo>();
//            int count = 0;
//            while ((tempString = reader.readLine()) != null){
//            	JSONObject jsonObject = JSONObject.parseObject(tempString);
//            	DocInfo docInfo = JSONObject.toJavaObject(jsonObject, DocInfo.class);
//            	docInfos.add(docInfo);
//            	if(docInfos.size() > 100000) {
//            		System.out.println("======count:" + count);
//            		WikidataService.insertWikidataDocInfo(docInfos);
//            		docInfos = new ArrayList<DocInfo>();
//            	}
//            	count++;
//            }
//            if(!docInfos.isEmpty()) {
//            	WikidataService.insertWikidataDocInfo(docInfos);
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if (reader != null){
//                try {
//                    reader.close();
//                }catch (IOException e){
//                	e.printStackTrace();
//                }
//            }
//        }
//    }
    
    public void readJsonFile2(String filePath) throws Exception{
        BufferedReader reader = null;
        
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            List<DocInfo> docInfos = new ArrayList<DocInfo>();
            int count = 0;
            int batchSize = 0;
            while ((tempString = reader.readLine()) != null){
            	JSONObject jsonObject = JSONObject.parseObject(tempString);
            	DocInfo docInfo = JSONObject.toJavaObject(jsonObject, DocInfo.class);
            	docInfos.add(docInfo);
            	if(docInfos.size() > 200000) {
            		System.out.println("=:" + count);
                    BufferedWriter docWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/docInfo_train_" + batchSize + ".txt", true));
                    BufferedWriter entityWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/entity_train_" + batchSize + ".txt", true));
                    BufferedWriter tripleWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/triple_train_" + batchSize + ".txt", true));
            		wikidataService.writeWikiToFileMultiThread(docInfos, docWriter, entityWriter, tripleWriter);
            		docInfos = new ArrayList<DocInfo>(); 
            		batchSize++;
            	}
            	++count;
            }
            if(docInfos.size() > 0) {
            	BufferedWriter docWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/docInfo_train_" + batchSize + ".txt", true));
                BufferedWriter entityWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/entity_train_" + batchSize + ".txt", true));
                BufferedWriter tripleWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/triple_train_" + batchSize + ".txt", true));
            	wikidataService.writeWikiToFileMultiThread(docInfos, docWriter, entityWriter, tripleWriter);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void readJsonFile3(String filePath) throws Exception{
        BufferedReader reader = null;
        
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            AtomicInteger count = new AtomicInteger(0);
            AtomicInteger batchSize = new AtomicInteger(0);
            List<DocInfo> docInfos = new ArrayList<DocInfo>();
            
            while ((tempString = reader.readLine()) != null){
            	JSONObject jsonObject = JSONObject.parseObject(tempString);
            	DocInfo docInfo = JSONObject.toJavaObject(jsonObject, DocInfo.class);
            	docInfos.add(docInfo);
            	if(docInfos.size() > 100000) {
            		System.out.println("=:" + count);
            		prepareThread(docInfos, count, batchSize);
            		docInfos = new ArrayList<DocInfo>(); 
            		batchSize.getAndIncrement();
            	}
            	count.getAndIncrement();
            }
            if(docInfos.size() > 0) {
        		prepareThread(docInfos, count, batchSize);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void prepareThread(List<DocInfo> docInfos, AtomicInteger count, AtomicInteger batchSize) {
    	threadPool.execute(() -> {
            try {
            	System.out.println("=:" + count);
                BufferedWriter docWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/docInfo_train_" + batchSize + ".txt", true));
                BufferedWriter entityWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/entity_train_" + batchSize + ".txt", true));
                BufferedWriter tripleWriter = new BufferedWriter(new FileWriter("src/main/resources/wikidata_train/triple_train_" + batchSize + ".txt", true));
        		wikidataService.writeWikiToFileMultiThread2(docInfos, docWriter, entityWriter, tripleWriter);
        		batchSize.getAndIncrement();
                TimeUnit.SECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName() + " is running and done.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    public void readJsonFile4(String filePath) throws Exception{
        BufferedReader reader = null;
        
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            int count = 0;
            int batchSize = 0;
            List<DocInfo> docInfos = new ArrayList<DocInfo>();
            
            while ((tempString = reader.readLine()) != null){
            	JSONObject jsonObject = JSONObject.parseObject(tempString);
            	DocInfo docInfo = JSONObject.toJavaObject(jsonObject, DocInfo.class);
            	docInfos.add(docInfo);
            	if(docInfos.size() > 100000) {
            		System.out.println("=:" + count);
            		prepareThread2(docInfos, count, batchSize);
            		docInfos = new ArrayList<DocInfo>(); 
            		batchSize++;
            	}
            	count++;
            }
            if(docInfos.size() > 0) {
            	prepareThread2(docInfos, count, batchSize);
            }
            
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void prepareThread2(List<DocInfo> docInfos, int count, int batchSize) {
        try {
        	System.out.println("=:" + count);
            BufferedWriter docWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/wikidata_train/docInfo_train_" + batchSize + ".txt"), "UTF-8"));
            BufferedWriter entityWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/wikidata_train/entity_train_" + batchSize + ".txt"), "UTF-8"));
            BufferedWriter tripleWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/wikidata_train/triple_train_" + batchSize + ".txt"), "UTF-8"));
            BufferedWriter relationWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/main/resources/wikidata_train/relation_train_" + batchSize + ".txt"), "UTF-8"));
    		wikidataService.writeWikiToFileMultiThread3(docInfos, docWriter, entityWriter, tripleWriter, relationWriter);
            TimeUnit.SECONDS.sleep(10);
            System.out.println(Thread.currentThread().getName() + " is running and done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  
}
