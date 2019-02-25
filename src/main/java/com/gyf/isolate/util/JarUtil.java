package com.gyf.isolate.util;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * @author yunfan.gyf
 **/
public class JarUtil {

    private final static String CONFIG_FILE_NAME = "config.prop";
    private static Logger logger = LoggerFactory.getLogger(JarUtil.class);

    public static void main(String[] args) throws IOException {
        readExportedClassFromConfig(new File("/Users/gaoyunfan/code/graduate/target/classes/myjarservice-v2-1.0-jar-with-dependencies.jar"));
    }


    /**
     * 判断Jar包是否是Bundle
     * @param url
     * @return
     */
    public static boolean isJarABundle(URL url) {
        File file = new File(url.getPath());
        if (file.isDirectory()) {
            return false;
        }
        try {
            JarFile jarFile = new JarFile(url.getPath());
            return jarFile.getJarEntry(CONFIG_FILE_NAME) != null;
        } catch (Exception e) {

        }
        return false;
    }


    public static Set<String> readExportedClassFromConfig(File file) throws IOException {
        JarFile jarFile = new JarFile(file);
        ZipEntry entry = jarFile.getEntry(CONFIG_FILE_NAME);
        if (entry == null) {
            logger.warn(file.getName()+" 没有bundle配置文件config.prop");
            return Sets.newHashSet();
        }
        InputStream inputStream = jarFile.getInputStream(entry);
        Properties properties = new Properties();
        properties.load(inputStream);
        String content = (String) properties.get("exported-class");
        HashSet<String> classes = Sets.newHashSet(content.split(";"));
        logger.debug("从" + file.getName() + "中找到导出类:" + classes);
        return classes;
    }



}
