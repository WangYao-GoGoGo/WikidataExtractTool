package com.wikidata.process.thread;

public interface ThreadPool {
	 	void execute(Runnable runnable);

	    void shutdown();

	    int getInitSize();

	    int getMaxSize();

	    int getCoreSize();

	    int getQueueSize();

	    int getActiveCount();

	    boolean isShutdown();
}
