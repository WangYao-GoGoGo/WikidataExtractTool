package com.wikidata.extractor.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class Triple {
	@SerializedName("seq")
	private int seq;
	@SerializedName("docid")
	private String docid;
	@SerializedName("subject")
	private Entity subject;
	@SerializedName("object")
	private Entity object;
	@SerializedName("predicate")
	private Entity predicate;
	@SerializedName("sentence_id")
	private String sentence_id;
	@SerializedName("dependency_path")
	private String dependency_path;
	@SerializedName("confidence")
	private String confidence;
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
	public String getDependency_path() {
		return dependency_path;
	}
	public void setDependency_path(String dependency_path) {
		this.dependency_path = dependency_path;
	}
	public String getConfidence() {
		return confidence;
	}
	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}
	public String getAnnotator() {
		return annotator;
	}
	public void setAnnotator(String annotator) {
		this.annotator = annotator;
	}
	public Entity getObject() {
		return object;
	}
	public void setObject(Entity object) {
		this.object = object;
	}
	public Entity getPredicate() {
		return predicate;
	}
	public void setPredicate(Entity predicate) {
		this.predicate = predicate;
	}
	public Entity getSubject() {
		return subject;
	}
	public void setSubject(Entity subject) {
		this.subject = subject;
	}
	public String getSentence_id() {
		return sentence_id;
	}
	public void setSentence_id(String sentence_id) {
		this.sentence_id = sentence_id;
	}
	@Override
	public String toString() {
		return "Triple [seq=" + seq + ", docid=" + docid + ", subject=" + subject + ", object=" + object
				+ ", predicate=" + predicate + ", sentence_id=" + sentence_id + ", dependency_path=" + dependency_path
				+ ", confidence=" + confidence + ", annotator=" + annotator + "]";
	}
	
}
