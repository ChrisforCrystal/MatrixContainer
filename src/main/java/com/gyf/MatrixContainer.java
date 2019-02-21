package com.gyf;


import com.gyf.hotswap.annotation.HotSwap;
import com.gyf.hotswap.classloader.HotSwapClassLoader;
import com.gyf.isolate.classloader.ContainerClassLoader;
import com.gyf.thread.IsolatedThreadGroup;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author yunfan.gyf
 **/
@HotSwap
public class MatrixContainer {

    private static final String CONTAINER_CLASS_LOADER_NAME = "com.gyf.isolate.classloader.ContainerClassLoader";
    public static void start(final String className, final String method) {
        if (isContainerStart()) {
            return;
        }
        IsolatedThreadGroup threadGroup = new IsolatedThreadGroup("MatrixContainerGroup");
        Thread thread = new Thread(threadGroup,new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程开始运行");
                    //todo
                    ContainerClassLoader cl = new ContainerClassLoader("/Users/gaoyunfan/code/graduate/target/classes");
                    Class<?> clazz = cl.loadClass(className);
                    Method startMethod = clazz.getMethod(method,String[].class);
                    startMethod.invoke(null,(Object)new String[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        join(threadGroup);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        while (!thread.isAlive()) {
//            System.out.println("Thread not start");
//        }
        System.out.println("主线程退出");
        threadGroup.rethrowUncaughtException();
        System.exit(0);
    }

    private static boolean isContainerStart() {
        Class<? extends ClassLoader> aClass = MatrixContainer.class.getClassLoader().getClass();
        return CONTAINER_CLASS_LOADER_NAME.equals(aClass.getCanonicalName());
    }

    public static void join(ThreadGroup threadGroup) {
        boolean hasNonDaemonThreads;
        do {
            hasNonDaemonThreads = false;
            Thread[] threads = new Thread[threadGroup.activeCount()];
            threadGroup.enumerate(threads);
            for (Thread thread : threads) {
                if (thread != null && !thread.isDaemon()) {
                    try {
                        hasNonDaemonThreads = true;
                        thread.join();
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } while (hasNonDaemonThreads);
    }
}
