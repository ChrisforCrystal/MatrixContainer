package com.gyf.hotswap.test;

import com.gyf.hotswap.classloader.HotSwapClassLoader;
import com.gyf.hotswap.interfacer.HotChangeItem;

/**
 * @author yunfan.gyf
 **/
public class HotSwapRunnable implements Runnable {
    private String className = "com.gyf.hotswap.test.Sweet";
    private HotChangeItem sweet;
    private HotSwapClassLoader hcl = null;

    @Override
    public void run() {
        try {
            while (true) {
                initLoad();
                sweet.sweet();
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLoad() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        hcl = HotSwapClassLoader.getClassLoader();
        sweet = reloadClass(hcl.loadClass(className).newInstance());
    }

    @SuppressWarnings("unchecked")
    public static <T> T reloadClass(Object o) {
        return (T) o;
    }

}
