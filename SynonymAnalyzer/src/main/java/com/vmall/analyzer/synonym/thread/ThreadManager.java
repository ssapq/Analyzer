package com.vmall.analyzer.synonym.thread;

/**
 * Created by shaosh on 2016/11/19.
 */
public class ThreadManager {

    private ThreadManager(){
        Runnable tr = new MonitorThread();
        Thread thread = new Thread(tr);
        thread.setDaemon(true); //设置守护线程
        thread.start(); //开始执行分进程
    }

    private static ThreadManager singleton;

    public static void work(){
        if(singleton == null){
            singleton = new ThreadManager();
        }
    }
}
