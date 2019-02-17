package com.gyf.hotswap.test;

import com.gyf.hotswap.classloader.HotSwapClassLoader;

import java.lang.reflect.Method;

/**
 * @author yunfan.gyf
 **/
public class HotSwapRunnable implements Runnable {
    private String className = "com.gyf.hotswap.test.Sweet";
    private Class clazz = null;
    private HotSwapClassLoader hcl = null;

    @Override
    public void run() {
        try {
            while (true) {
                initLoad();
                Object o = clazz.newInstance();
                Method sweet = clazz.getMethod("sweet");
                sweet.invoke(o);
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLoad() throws ClassNotFoundException {
        hcl = HotSwapClassLoader.getClassLoader();
        clazz = hcl.loadClass(className);
    }
}
