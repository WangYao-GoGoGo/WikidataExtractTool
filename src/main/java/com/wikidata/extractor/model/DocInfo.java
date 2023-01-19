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
public class DocInfo {
	@SerializedName("docid")
	private String docid;
	@SerializedName("title")
	private String title;
	@SerializedName("uri")
	private String uri;
	@SerializedName("text")
	private String text;
	@SerializedName("entities")
	private List<Entity> entities;
	@SerializedName("triples")
	private List<Triple> triples;
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Entity> getEntities() {
		return entities;
	}
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}
	public List<Triple> getTriples() {
		return triples;
	}
	public void setTriples(List<Triple> triples) {
		this.triples = triples;
	}
	@Override
	public String toString() {
		return "DocInfo [docid=" + docid + ", title=" + title + ", uri=" + uri + ", text=" + text + ", entities="
				+ entities + ", triples=" + triples + "]";
	}
}
