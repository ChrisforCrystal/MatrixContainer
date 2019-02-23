package com.gyf.isolate.item;

import java.net.URL;
import java.util.Set;

/**
 * @author yunfan.gyf
 **/
public class Bundle {
    private String bundleName;

    private Set<String> exportClasses;

    private URL[] urls;

    private ClassLoader classLoader;

    public Bundle() {
    }

    public String getBundleName() {
        return bundleName;
    }

    public Bundle setBundleName(String bundleName) {
        this.bundleName = bundleName;
        return this;
    }

    public Set<String> getExportClasses() {
        return exportClasses;
    }

    public Bundle setExportClasses(Set<String> exportClasses) {
        this.exportClasses = exportClasses;
        return this;

    }

    public URL[] getUrls() {
        return urls;
    }

    public Bundle setUrls(URL[] urls) {
        this.urls = urls;
        return this;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public Bundle setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        return this;
    }
}
