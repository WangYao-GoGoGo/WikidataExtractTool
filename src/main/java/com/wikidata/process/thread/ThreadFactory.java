package com.wikidata.process.thread;

public interface ThreadFactory {
	Thread createThread(Runnable runnable);
}
