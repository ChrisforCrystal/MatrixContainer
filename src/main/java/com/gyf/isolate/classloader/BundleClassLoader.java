package com.gyf.isolate.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yunfan.gyf
 **/
public class BundleClassLoader extends URLClassLoader {
    public BundleClassLoader(String name, URL[] urls) {
        super(urls);
    }

    private static URL[] getClassPath(String classpath) {
        return new URL[0];
    }
}
