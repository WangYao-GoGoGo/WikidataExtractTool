package com.wikidata.process.thread;

public interface RunnableQueue {
	void offer(Runnable runnable);

    Runnable take() throws InterruptedException;

    int size();
}
