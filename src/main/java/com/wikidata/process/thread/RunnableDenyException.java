package com.wikidata.process.thread;

@SuppressWarnings("serial")
public class RunnableDenyException extends RuntimeException {
	public RunnableDenyException(String message) {
        super(message);
    }
}
