package com.itonglian.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomThreadPool {

    private static class CustomThreadPoolHolder{
        private static CustomThreadPool customThreadPool=new CustomThreadPool();
    }

    public static CustomThreadPool getInstance(){
        return CustomThreadPoolHolder.customThreadPool;
    }

    private final static ExecutorService executorService = Executors.newCachedThreadPool();

    public ExecutorService getExecutorService(){
        return executorService;
    }
}
