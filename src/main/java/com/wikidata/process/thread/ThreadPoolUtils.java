package com.wikidata.process.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolUtils {

	private static int taskCount = 15;
    // actual completed task count
    private static AtomicInteger taskCountExecuted;

    public static void main(String[] args) {
        init();
    }

    private static void init(){
        taskCountExecuted = new AtomicInteger(0);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,        // core thread count
                15,    // max thread count
                5,        // Non-core thread recycling timeout
                TimeUnit.SECONDS,     // timeout unit
                new ArrayBlockingQueue<>(30)      // task queue
        );

        System.out.println("task total count = [" + taskCount + "]");
        long start = System.currentTimeMillis();

        for(int i=0; i<taskCount; i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(500);
                        System.out.println("executed task count [" + taskCountExecuted.addAndGet(1) + "] ");
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            };

            try{
                // default:rejection will be error
                threadPoolExecutor.execute(runnable);
            }catch (Exception ex){
            	ex.printStackTrace();
                taskCount = threadPoolExecutor.getActiveCount() + threadPoolExecutor.getQueue().size();
            }
        }

        long end = 0;
        while (threadPoolExecutor.getCompletedTaskCount() < taskCount){
            end = System.currentTimeMillis();
        }

        System.out.println("[" + taskCountExecuted + "]the total number tasks time cost= [" + (end - start) + "]ms");
        threadPoolExecutor.shutdown();
    }

}
