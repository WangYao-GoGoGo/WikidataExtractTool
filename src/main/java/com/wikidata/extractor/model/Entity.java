package com.wikidata.extractor.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class Entity {
	@SerializedName("seq")
	private int seq;
	@SerializedName("docid")
	private String docid;
	@SerializedName("uri")
	private String uri;
	@SerializedName("boundaries")
	private List<String> boundaries;
	@SerializedName("boundaries_st")
	private String boundaries_st;
	@SerializedName("boundaries_ed")
	private String boundaries_ed;
	@SerializedName("surfaceform")
	private String surfaceform;
	@SerializedName("annotator")
	private String annotator;
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getBoundaries_st() {
		return boundaries_st;
	}
	public void setBoundaries_st(String boundaries_st) {
		this.boundaries_st = boundaries_st;
	}
	public String getBoundaries_ed() {
		return boundaries_ed;
	}
	public void setBoundaries_ed(String boundaries_ed) {
		this.boundaries_ed = boundaries_ed;
	}
	public String getSurfaceform() {
		return surfaceform;
	}
	public void setSurfaceform(String surfaceform) {
		this.surfaceform = surfaceform;
	}
	public String getAnnotator() {
		return annotator;
	}
	public void setAnnotator(String annotator) {
		this.annotator = annotator;
	}
	public List<String> getBoundaries() {
		return boundaries;
	}
	public void setBoundaries(List<String> boundaries) {
		this.boundaries = boundaries;
	}
	@Override
	public String toString() {
		return "Entity [seq=" + seq + ", docid=" + docid + ", uri=" + uri + ", boundaries=" + boundaries
				+ ", boundaries_st=" + boundaries_st + ", boundaries_ed=" + boundaries_ed + ", surfaceform="
				+ surfaceform + ", annotator=" + annotator + "]";
	}
	
}
