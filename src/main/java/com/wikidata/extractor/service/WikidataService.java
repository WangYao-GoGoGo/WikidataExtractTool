package com.wikidata.extractor.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.wikidata.extractor.db.MysqlCon;
import com.wikidata.extractor.model.DocInfo;
import com.wikidata.extractor.model.Entity;
import com.wikidata.extractor.model.Triple;
import com.wikidata.extractor.utils.RegexUtils;
import com.wikidata.process.thread.BasicThreadPool;
import com.wikidata.process.thread.ThreadPool;

public class WikidataService {
	
	final ThreadPool threadPool = new BasicThreadPool(8, 6, 4, 1000);
	
	@SuppressWarnings("rawtypes")
	public void insertWikidataDocInfo(List<DocInfo> docInfos) throws Exception {
		String sql = "INSERT INTO tb_wikidata_train_docinfo (docid,title,uri,text) VALUES(?,?,?,?)";
		String sqlEntity = "INSERT INTO tb_wikidata_train_entities (uri,boundaries_st,boundaries_ed,boundaries,surfaceform,annotator) VALUES(?,?,?,?,?,?)";
		String sqlTriple = "INSERT INTO tb_wikidata_train_triples (docid,subject,object,predicate,sentence_id,dependency_path,confidence,annotator) VALUES(?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement psEntity = null;
		PreparedStatement psTriple = null;
		try {
			//docInfo
			conn = MysqlCon.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			psEntity = conn.prepareStatement(sqlEntity);
			psTriple = conn.prepareStatement(sqlTriple);

			for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
				DocInfo docInfo = (DocInfo) iterator.next();
				ps.setString(1, docInfo.getDocid());
				ps.setString(2, docInfo.getTitle());
				ps.setString(3, docInfo.getUri());
				ps.setString(4, docInfo.getText());
				//Entity
				if(docInfo.getEntities() != null & docInfo.getEntities().size() > 0) {
					for (Iterator entityIterator = docInfo.getEntities().iterator(); entityIterator.hasNext();) {
						Entity entity = (Entity) entityIterator.next();
						psEntity.setString(1, entity.getUri());
						List<String> boundaries = entity.getBoundaries();
						if(boundaries != null) {
							String boundaries_st = boundaries.get(0);
							String boundaries_ed = boundaries.get(1);
							psEntity.setString(2, boundaries_st);
							psEntity.setString(3, boundaries_ed);
							psEntity.setString(4, boundaries_st+"," + boundaries_ed);
						} else {
							psEntity.setString(2, null);
							psEntity.setString(3, null);
							psEntity.setString(4, null);
						}
						psEntity.setString(5, entity.getSurfaceform());
						psEntity.setString(6, entity.getAnnotator());
						psEntity.addBatch();
					}
				}
				
				//Triples
				if(docInfo.getTriples() != null & docInfo.getTriples().size() > 0) {
					for (Iterator triplesIterator = docInfo.getTriples().iterator(); triplesIterator.hasNext();) {
						Triple triple = (Triple) triplesIterator.next();
						psTriple.setString(1, docInfo.getDocid());
						psTriple.setString(2, triple.getSubject().getUri());
						psTriple.setString(3, triple.getObject().getUri());
						psTriple.setString(4, triple.getPredicate().getUri());
						psTriple.setString(5, triple.getSentence_id());
						psTriple.setString(6, triple.getDependency_path());
						psTriple.setString(7, triple.getConfidence());
						psTriple.setString(8, triple.getAnnotator());
						psTriple.addBatch();
					}
				}
				ps.addBatch();
			}
			ps.executeBatch();
			psTriple.executeBatch();
			psEntity.executeBatch();
			
			ps.clearBatch();
			psTriple.clearBatch();
			psEntity.clearBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			MysqlCon.close(conn, ps);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void writeWikiToFile(List<DocInfo> docInfos, BufferedWriter docWriter, BufferedWriter entityWriter, BufferedWriter tripleWriter) throws Exception {
		//docInfo
		for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
			DocInfo docInfo = (DocInfo) iterator.next();
			StringBuffer docSBuffer = new StringBuffer();
			docSBuffer
			.append(docInfo.getDocid()).append("x'01'")
			.append(docInfo.getTitle()).append("x'01'")
			.append(docInfo.getUri()).append("x'01'")
			.append(docInfo.getText()).append("\n");
			writeFile(docWriter, docSBuffer.toString());
			
			//Entity
			if(docInfo.getEntities() != null & docInfo.getEntities().size() > 0) {
				for (Iterator entityIterator = docInfo.getEntities().iterator(); entityIterator.hasNext();) {
					Entity entity = (Entity) entityIterator.next();
					StringBuffer entitySBuffer = new StringBuffer();
					entitySBuffer
					.append(docInfo.getUri()).append("x'01'");
					List<String> boundaries = entity.getBoundaries();
					if(boundaries != null) {
						String boundaries_st = boundaries.get(0);
						String boundaries_ed = boundaries.get(1);
						entitySBuffer
						.append(boundaries_st).append("x'01'");
						entitySBuffer
						.append(boundaries_ed).append("x'01'");
						entitySBuffer
						.append(boundaries_st+"," + boundaries_ed).append("x'01'");
					} else {
						entitySBuffer
						.append("").append("x'01'");
						entitySBuffer
						.append("").append("x'01'");
						entitySBuffer
						.append("").append("x'01'");
					}
					entitySBuffer.append(entity.getSurfaceform()).append("x'01'")
					.append(entity.getAnnotator()).append("\n");
					writeFile(entityWriter, entitySBuffer.toString());
				}
			}
			
			//Triples
			if(docInfo.getTriples() != null & docInfo.getTriples().size() > 0) {
				for (Iterator triplesIterator = docInfo.getTriples().iterator(); triplesIterator.hasNext();) {
					Triple triple = (Triple) triplesIterator.next();
					StringBuffer tripleSBuffer = new StringBuffer();
					tripleSBuffer
					.append(docInfo.getDocid()).append("x'01'")
					.append(triple.getSubject().getUri()).append("x'01'")
					.append(triple.getObject().getUri()).append("x'01'")
					.append(triple.getPredicate().getUri()).append("x'01'")
					.append(triple.getSentence_id()).append("x'01'")
					.append(triple.getDependency_path()).append("x'01'")
					.append(triple.getDependency_path()).append("x'01'")
					.append(triple.getAnnotator()).append("\n");
					writeFile(tripleWriter, tripleSBuffer.toString());
				}
			}
		}
	} 
	
	@SuppressWarnings("rawtypes")
	public void writeWikiToFileMultiThread(List<DocInfo> docInfos, BufferedWriter docWriter, BufferedWriter entityWriter, BufferedWriter tripleWriter) throws Exception {
		
		new Thread(new Runnable() {
            @Override
            public void run() {
            	//docInfo
        		for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
        			DocInfo docInfo = (DocInfo) iterator.next();
        			StringBuffer docSBuffer = new StringBuffer();
        			docSBuffer
        			.append(docInfo.getDocid()).append("x'01'")
        			.append(docInfo.getTitle()).append("x'01'")
        			.append(docInfo.getUri()).append("x'01'")
        			.append(docInfo.getText());
        			writeFile(docWriter, docSBuffer.toString());
        		}	
        		try {
					docWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }).start();
		
		new Thread(new Runnable() {
            @Override
            public void run() {
            	//docInfo
        		for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
        			DocInfo docInfo = (DocInfo) iterator.next();
        			//Entity
        			if(docInfo.getEntities() != null & docInfo.getEntities().size() > 0) {
        				for (Iterator entityIterator = docInfo.getEntities().iterator(); entityIterator.hasNext();) {
        					Entity entity = (Entity) entityIterator.next();
        					StringBuffer entitySBuffer = new StringBuffer();
        					entitySBuffer
        					.append(entity.getUri()).append("x'01'");
        					List<String> boundaries = entity.getBoundaries();
        					if(boundaries != null) {
        						String boundaries_st = boundaries.get(0);
        						String boundaries_ed = boundaries.get(1);
        						entitySBuffer
        						.append(boundaries_st).append("x'01'");
        						entitySBuffer
        						.append(boundaries_ed).append("x'01'");
        						entitySBuffer
        						.append(boundaries_st+"," + boundaries_ed).append("x'01'");
        					} else {
        						entitySBuffer
        						.append("").append("x'01'");
        						entitySBuffer
        						.append("").append("x'01'");
        						entitySBuffer
        						.append("").append("x'01'");
        					}
        					entitySBuffer.append(entity.getSurfaceform()).append("x'01'")
        					.append(entity.getAnnotator());
        					writeFile(entityWriter, entitySBuffer.toString());
        				}
        			}
        		}	
        		
        		try {
        			entityWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }).start();
		
		new Thread(new Runnable() {
            @Override
            public void run() {
            	//docInfo
        		for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
        			DocInfo docInfo = (DocInfo) iterator.next();
        			//Triples
        			if(docInfo.getTriples() != null & docInfo.getTriples().size() > 0) {
        				for (Iterator triplesIterator = docInfo.getTriples().iterator(); triplesIterator.hasNext();) {
        					Triple triple = (Triple) triplesIterator.next();
        					StringBuffer tripleSBuffer = new StringBuffer();
        					tripleSBuffer
        					.append(docInfo.getDocid()).append("x'01'")
        					.append(triple.getSubject().getUri()).append("x'01'")
        					.append(triple.getObject().getUri()).append("x'01'")
        					.append(triple.getPredicate().getUri()).append("x'01'")
        					.append(triple.getSentence_id()).append("x'01'")
        					.append(triple.getDependency_path()).append("x'01'")
        					.append(triple.getDependency_path()).append("x'01'")
        					.append(triple.getAnnotator());
        					writeFile(tripleWriter, tripleSBuffer.toString());
        				}
        			}
        		}	
        		try {
        			tripleWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }).start();
	} 
	
	@SuppressWarnings("rawtypes")
	public void writeWikiToFileMultiThread2(List<DocInfo> docInfos, BufferedWriter docWriter, BufferedWriter entityWriter, BufferedWriter tripleWriter) throws Exception {
		//docInfo
		for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
			DocInfo docInfo = (DocInfo) iterator.next();
			StringBuffer docSBuffer = new StringBuffer();
			docSBuffer
			.append(docInfo.getDocid()).append("x'01'")
			.append(docInfo.getTitle()).append("x'01'")
			.append(docInfo.getUri()).append("x'01'")
			.append(docInfo.getText()).append("\n");
			writeFile(docWriter, docSBuffer.toString());
			
			//Entity
			if(docInfo.getEntities() != null & docInfo.getEntities().size() > 0) {
				for (Iterator entityIterator = docInfo.getEntities().iterator(); entityIterator.hasNext();) {
					Entity entity = (Entity) entityIterator.next();
					StringBuffer entitySBuffer = new StringBuffer();
					entitySBuffer
					.append(docInfo.getUri()).append("x'01'");
					List<String> boundaries = entity.getBoundaries();
					if(boundaries != null) {
						String boundaries_st = boundaries.get(0);
						String boundaries_ed = boundaries.get(1);
						entitySBuffer
						.append(boundaries_st).append("x'01'");
						entitySBuffer
						.append(boundaries_ed).append("x'01'");
						entitySBuffer
						.append(boundaries_st+"," + boundaries_ed).append("x'01'");
					} else {
						entitySBuffer
						.append("").append("x'01'");
						entitySBuffer
						.append("").append("x'01'");
						entitySBuffer
						.append("").append("x'01'");
					}
					entitySBuffer.append(entity.getSurfaceform()).append("x'01'")
					.append(entity.getAnnotator()).append("\n");
					writeFile(entityWriter, entitySBuffer.toString());
				}
			}
			
			//Triples
			if(docInfo.getTriples() != null & docInfo.getTriples().size() > 0) {
				for (Iterator triplesIterator = docInfo.getTriples().iterator(); triplesIterator.hasNext();) {
					Triple triple = (Triple) triplesIterator.next();
					StringBuffer tripleSBuffer = new StringBuffer();
					tripleSBuffer
					.append(docInfo.getDocid()).append("x'01'")
					.append(triple.getSubject().getUri()).append("x'01'")
					.append(triple.getObject().getUri()).append("x'01'")
					.append(triple.getPredicate().getUri()).append("x'01'")
					.append(triple.getSentence_id()).append("x'01'")
					.append(triple.getDependency_path()).append("x'01'")
					.append(triple.getDependency_path()).append("x'01'")
					.append(triple.getAnnotator()).append("\n");
					writeFile(tripleWriter, tripleSBuffer.toString());
				}
			}
		}
	} 
	
	public void writeFile(BufferedWriter writer, String fileContent) {
	    try {
	    	writer.write(fileContent);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	@SuppressWarnings("rawtypes")
	public void writeWikiToFileMultiThread3(List<DocInfo> docInfos, BufferedWriter docWriter, BufferedWriter entityWriter, BufferedWriter tripleWriter, BufferedWriter relationWriter) throws Exception {
		
		threadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
                System.out.println(Thread.currentThread().getName() + " is running and done.");
              //docInfo
        		for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
        			DocInfo docInfo = (DocInfo) iterator.next();
        			StringBuffer docSBuffer = new StringBuffer();
        			docSBuffer
        			.append(RegexUtils.replaceBlank(docInfo.getDocid())).append("x'01'")
        			.append(RegexUtils.replaceBlank(docInfo.getTitle())).append("x'01'")
        			.append(RegexUtils.replaceBlank(docInfo.getUri())).append("x'01'")
        			.append(RegexUtils.replaceBlank(docInfo.getText())).append("\r\n");
        			writeFile(docWriter, docSBuffer.toString());
        		}	
        		try {
					docWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		
		threadPool.execute(() -> {
        	//docInfo
    		for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
    			DocInfo docInfo = (DocInfo) iterator.next();
    			//Entity
    			if(docInfo.getEntities() != null & docInfo.getEntities().size() > 0) {
    				for (Iterator entityIterator = docInfo.getEntities().iterator(); entityIterator.hasNext();) {
    					Entity entity = (Entity) entityIterator.next();
    					StringBuffer entitySBuffer = new StringBuffer();
    					entitySBuffer
    					.append(RegexUtils.replaceBlank(entity.getUri())).append("x'01'");
    					List<String> boundaries = entity.getBoundaries();
    					if(boundaries != null) {
    						String boundaries_st = boundaries.get(0);
    						String boundaries_ed = boundaries.get(1);
    						entitySBuffer
    						.append(boundaries_st).append("x'01'");
    						entitySBuffer
    						.append(boundaries_ed).append("x'01'");
    						entitySBuffer
    						.append(boundaries_st+"," + boundaries_ed).append("x'01'");
    					} else {
    						entitySBuffer
    						.append("").append("x'01'");
    						entitySBuffer
    						.append("").append("x'01'");
    						entitySBuffer
    						.append("").append("x'01'");
    					}
    					entitySBuffer.append(RegexUtils.replaceBlank(entity.getSurfaceform())).append("x'01'")
    					.append(RegexUtils.replaceBlank(entity.getAnnotator())).append("\r\n");;
    					writeFile(entityWriter, entitySBuffer.toString());
    				}
    			}
    		}	
    		
    		try {
    			entityWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		threadPool.execute(() -> {
        	//docInfo
    		for (Iterator iterator = docInfos.iterator(); iterator.hasNext();) {
    			DocInfo docInfo = (DocInfo) iterator.next();
    			//Triples
    			if(docInfo.getTriples() != null & docInfo.getTriples().size() > 0) {
    				for (Iterator triplesIterator = docInfo.getTriples().iterator(); triplesIterator.hasNext();) {
    					//triples
    					Triple triple = (Triple) triplesIterator.next();
    					StringBuffer tripleSBuffer = new StringBuffer();
    					tripleSBuffer
    					.append(docInfo.getDocid()).append("x'01'")
    					.append(RegexUtils.replaceBlank(triple.getSubject().getUri())).append("x'01'")
    					.append(RegexUtils.replaceBlank(triple.getObject().getUri())).append("x'01'")
    					.append(RegexUtils.replaceBlank(triple.getPredicate().getUri())).append("x'01'")
    					.append(triple.getSentence_id()).append("x'01'")
    					.append(RegexUtils.replaceBlank(triple.getDependency_path())).append("x'01'")
    					.append(RegexUtils.replaceBlank(triple.getConfidence())).append("x'01'")
    					.append(RegexUtils.replaceBlank(triple.getAnnotator())).append("\r\n");;
    					writeFile(tripleWriter, tripleSBuffer.toString());
    					
    					// relations
    					Entity relation = triple.getPredicate();
    					StringBuffer relationSBuffer = new StringBuffer();
    					relationSBuffer
    					.append(RegexUtils.replaceBlank(relation.getUri())).append("x'01'");
    					relationSBuffer.append(RegexUtils.replaceBlank(relation.getSurfaceform())).append("x'01'")
    					.append(RegexUtils.replaceBlank(relation.getAnnotator())).append("\r\n");;
    					writeFile(relationWriter, relationSBuffer.toString());
    				}
    			}
    		}	
    		try {
    			tripleWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
