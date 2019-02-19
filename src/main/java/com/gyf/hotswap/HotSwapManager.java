package com.gyf.hotswap;

import com.gyf.hotswap.classloader.HotSwapClassLoader;
import com.gyf.hotswap.test.Sweet;

/**
 * @author yunfan.gyf
 **/
public class HotSwapManager {
    private static HotSwapClassLoader hcl;

    public static <T>T getBean(Class<T> clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String name = clazz.getCanonicalName();
        hcl = HotSwapClassLoader.getClassLoader();
        return reloadClass(hcl.loadClass(name).newInstance());
    }

    @SuppressWarnings("unchecked")
    public static <T> T reloadClass(Object o) {
        return (T) o;
    }
    public static void main(String[] args) {
    }
}
