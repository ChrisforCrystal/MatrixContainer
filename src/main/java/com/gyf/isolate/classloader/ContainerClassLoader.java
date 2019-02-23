package com.gyf.isolate.classloader;

import com.gyf.isolate.Test;
import com.gyf.isolate.service.ClassLoaderService;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yunfan.gyf
 **/
public class ContainerClassLoader extends URLClassLoader {
    private ClassLoaderService classLoaderService;

    public ContainerClassLoader() throws MalformedURLException {
        super(getClassPath());
        classLoaderService = ClassLoaderService.getInstance();

    }

    private static URL[] getClassPath() throws MalformedURLException {
        return ((URLClassLoader) getSystemClassLoader()).getURLs();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name,false);
    }

    @Override
    public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
        Class clazz=null;
        try {
             clazz = customLoad(name, false, this);
        } catch (Exception e) {

        }
        if (clazz == null) {
            try {
                ClassLoader system = ClassLoader.getSystemClassLoader();
                clazz = system.loadClass(name);
                if (clazz != null) {
                    if (resolve) {
                        //负责完成Class对象的链接
                        resolveClass(clazz);
                    }
                    return clazz;
                }
            } catch (ClassNotFoundException e) {

            }
        }
        return clazz;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    private Class customLoad(String name, boolean resolve, ClassLoader hcl) throws MalformedURLException, ClassNotFoundException {
        return null;
    }
}
