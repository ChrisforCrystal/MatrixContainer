package com.gyf.isolate.service;

import com.google.common.collect.Lists;
import com.gyf.isolate.item.Bundle;
import com.gyf.isolate.util.JarUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yunfan.gyf
 **/
public class ClassLoaderService {

    private static Logger logger = Logger.getLogger(ClassLoaderService.class);
    /**
     * 每个bundle共享出来隔离的类及其类加载器
     */
    private volatile static ClassLoaderService classLoaderService;

    private ConcurrentHashMap<String, ClassLoader> sharedClassAndClassLoaderMap = new ConcurrentHashMap<>();

    private ClassLoader jdkClassloader;
    private ClassLoader systemClassloader;
    private BundleService bundleService;

    private ClassLoaderService() {
        bundleService = BundleService.getInstance();
        init();
    }

    private void init() {
        prepareJdkClassloader();
        createBundleFromClassPath(((URLClassLoader) systemClassloader).getURLs());
    }

    private void createBundleFromClassPath(URL[] urls) {
        for (URL url : urls) {
            if (JarUtil.isJarABundle(url)) {
                try {
                    Bundle bundle = bundleService.createBundle(new File(url.toURI()));
                    for (String exportClass : bundle.getExportClasses()) {
                        sharedClassAndClassLoaderMap.putIfAbsent(exportClass, bundle.getClassLoader());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("创建 " + url.getPath() + " Bundle失败");
                }
                logger.info("创建 " + url.getPath() + " Bundle 成功");
            }
        }
        logger.debug(" map 创建完毕 "+sharedClassAndClassLoaderMap);
    }

    /**
     * 初始化jdkClassLoader
     */
    private void prepareJdkClassloader() {
        systemClassloader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassloader = systemClassloader;
        while (extClassloader.getParent() != null) {
            extClassloader = extClassloader.getParent();
        }

        List<URL> jdkUrls = Lists.newArrayList();
        try {
            String javaHome = System.getProperty("java.home").replace(File.separator + "jre", "");
            URL[] urLs = ((URLClassLoader) systemClassloader).getURLs();
            for (URL urL : urLs) {
                if (urL.getPath().startsWith(javaHome)) {
                    logger.debug("加载 JDK URL: " + urL);
                    jdkUrls.add(urL);
                }
            }
        } catch (Throwable throwable) {
            logger.warn("加载JDK URL时出错");
        }
        jdkClassloader = new URLClassLoader(jdkUrls.toArray(new URL[0]), extClassloader);
        logger.debug("ClassLoaderService 初始化成功");
    }

    public static void main(String[] args) {
        ClassLoaderService service = ClassLoaderService.getInstance();
    }

    /**
     * 单例模式确保容器内只有一个Service
     *
     * @return
     */
    public static ClassLoaderService getInstance() {
        if (classLoaderService == null) {
            synchronized (ClassLoaderService.class) {
                if (classLoaderService == null) {
                    classLoaderService = new ClassLoaderService();
                }
            }
        }
        return classLoaderService;
    }

}
