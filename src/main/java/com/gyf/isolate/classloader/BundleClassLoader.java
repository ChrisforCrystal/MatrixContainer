package com.gyf.isolate.classloader;

import cn.hutool.core.util.StrUtil;
import com.gyf.isolate.service.ClassLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yunfan.gyf
 **/
public class BundleClassLoader extends URLClassLoader {
    private String bundleName;
    private static Logger logger = LoggerFactory.getLogger(BundleClassLoader.class);
    private ClassLoaderService classLoaderService = ClassLoaderService.getInstance();

    public BundleClassLoader(String name, URL[] urls) {
        super(urls);
        bundleName = name;
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (StrUtil.isEmpty(name)) {
            return null;
        }
        Class<?> clazz = findLoadedClass(name);

        if (clazz == null) {
            clazz = resolveJDKClass(name);
        }

        if (clazz == null) {
            clazz = resolveLocalClass(name);
        }

        if (clazz != null) {
            if (resolve) {
                super.resolveClass(clazz);
            }
            return clazz;
        }
        logger.error("ContainerClassLoader 加载 " + name + "失败");
        throw new ClassNotFoundException("ContainerClassLoader cannot load class " + name);

    }

    private Class<?> resolveLocalClass(String name) {
        try {
            logger.debug(this + " 加载 " + name);
            return super.findClass(name);
        } catch (ClassNotFoundException e) {

        }
        return null;
    }

//    private Class<?> resolveExportClass(String name) {
//        ClassLoader exportClassLoader = classLoaderService.findExportClassLoader(name);
//        if (exportClassLoader != null) {
//            try {
//                Class<?> aClass = exportClassLoader.loadClass(name);
//                logger.debug(exportClassLoader + " 加载 " + name);
//                return aClass;
//            } catch (ClassNotFoundException e) {
//
//            }
//        }
//        return null;
//    }

    private Class<?> resolveJDKClass(String name) {
        try {
            Class<?> aClass = classLoaderService.getJdkClassloader().loadClass(name);
            logger.debug("JDK ClassLoader 加载 " + name);
            return aClass;
        } catch (ClassNotFoundException e) {

        }
        return null;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }
}
