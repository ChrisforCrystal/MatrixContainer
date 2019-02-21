package com.gyf.isolate.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yunfan.gyf
 **/
public class ContainerClassLoader extends URLClassLoader {
    public ContainerClassLoader(String classpath) throws MalformedURLException {
        super(getClassPath(classpath));
    }

    private static URL[] getClassPath(String classpath) throws MalformedURLException {
        return new URL[]{new File("/Users/gaoyunfan/code/graduate/target/classes").toURI().toURL()};
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

    private Class customLoad(String name, boolean resolve, ClassLoader hcl) throws ClassNotFoundException {
        int i;
        Class clazz = ((ContainerClassLoader) hcl).findClass(name);
        if (resolve) {
            ((ContainerClassLoader) hcl).resolveClass(clazz);
        }
        return clazz;
    }
}
