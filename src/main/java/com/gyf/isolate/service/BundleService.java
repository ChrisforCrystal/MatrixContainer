package com.gyf.isolate.service;

import com.gyf.isolate.classloader.BundleClassLoader;
import com.gyf.isolate.item.Bundle;
import com.gyf.isolate.util.JarUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

/**
 * @author yunfan.gyf
 **/
public class BundleService {

    private static BundleService bundleService;


    public static Bundle createBundle(File file) throws IOException {
        JarFile jarFile = new JarFile(file);
        Bundle bundle = new Bundle();
        bundle.setBundleName(file.getName())
                .setUrls(new URL[]{file.toURI().toURL()})
                .setExportClasses(JarUtil.readExportedClassFromConfig(file))
                .setClassLoader(new BundleClassLoader(bundle.getBundleName(),bundle.getUrls()));
        return bundle;
    }

    public static BundleService getInstance() {
        if (bundleService == null) {
            synchronized (BundleService.class) {
                if (bundleService == null) {
                    bundleService = new BundleService();
                }
            }
        }
        return bundleService;
    }
}
