package com.gyf.isolate.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author yunfan.gyf
 **/
public class JarUtil {
    public static void main(String[] args) throws IOException {
        JarFile jarFile = new JarFile("/Users/gaoyunfan/code/graduate/target/classes/myjarservice-v1-1.0-jar-with-dependencies.jar");
        Enumeration<JarEntry> jarEntrys = jarFile.entries();
        while (jarEntrys.hasMoreElements()) {
            JarEntry entry = jarEntrys.nextElement();
            String name = entry.getName();
            System.out.println(name);
        }
    }
}
