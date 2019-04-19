/**
 * Project Name:easytnt-commons
 * File Name:ThreadExecutor.java
 * Package Name:com.ez.commons.thread
 * Date:2016年3月30日上午10:46:44
 * Copyright (c) 2016, easytnt All Rights Reserved.
 */
package com.newyu.utils.thread;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: ThreadExecutor <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2016年3月30日 上午10:46:44 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class ThreadExecutor {
    private static ThreadExecutor threadExecutor = new ThreadExecutor();
    private ExecutorService executorService;

    private ThreadExecutor() {
        executorService = new ThreadPoolExecutor(0,
                Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                createThreadFactory());
    }

    public static ThreadExecutor newInstance() {
        return threadExecutor;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public ListeningExecutorService getListeningExecutorService() {
        return MoreExecutors.listeningDecorator(executorService);
    }

    public static ThreadFactory createThreadFactory() {
        return new MyThreadFactory();
    }

    private static class MyThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNo = new AtomicInteger();

        @Override
        public Thread newThread(Runnable runner) {
            SecurityManager manager = System.getSecurityManager();
            ThreadGroup group = manager != null ? manager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            Thread thread = new Thread(group, runner, "newyu-Thread-" + poolNo.getAndIncrement() + "-");
            if (thread.isDaemon()) {
                thread.setDaemon(Boolean.FALSE);
            }
            if (thread.getPriority() != Thread.NORM_PRIORITY) {
                thread.setPriority(Thread.NORM_PRIORITY);
            }
            return thread;
        }

    }
}
