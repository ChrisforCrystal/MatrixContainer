package com.gyf.isolate.classloader;

import cn.hutool.core.util.StrUtil;
import com.gyf.isolate.service.ClassLoaderService;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yunfan.gyf
 **/
public class ContainerClassLoader extends URLClassLoader {
    private static Logger logger = Logger.getLogger(ContainerClassLoader.class);
    private ClassLoaderService classLoaderService = ClassLoaderService.getInstance();

    public ContainerClassLoader(URL[] urLs) throws MalformedURLException {
        super(urLs, null);
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
            clazz = resolveExportClass(name);
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

    private Class<?> resolveExportClass(String name) {
        ClassLoader exportClassLoader = classLoaderService.findExportClassLoader(name);
        if (exportClassLoader != null) {
            try {
                Class<?> aClass = exportClassLoader.loadClass(name);
                logger.debug(exportClassLoader + " 加载 " + name);
                return aClass;
            } catch (ClassNotFoundException e) {

            }
        }
        return null;
    }

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
