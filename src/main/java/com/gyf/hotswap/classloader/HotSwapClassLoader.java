package com.gyf.hotswap.classloader;

import com.gyf.hotswap.annotation.HotSwap;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yunfan.gyf
 **/
public class HotSwapClassLoader extends URLClassLoader {

    //class文件的最后加载时间
    public static Map<String, Long> cacheLastModifyTimeMap = new HashMap<>();

    /**
     * 编译生成的class类所在的路径
     */
    public final static String projectClassPath = "/Users/gaoyunfan/code/graduate/target/classes";

    public final static String packagePath = "/";

    private static HotSwapClassLoader hcl = new HotSwapClassLoader();

    public HotSwapClassLoader(String scanPath) {

        super(getMyURLs());
    }

    public HotSwapClassLoader() {
        super(getMyURLs());
    }

    public static HotSwapClassLoader getClassLoader() {
        return hcl;
    }

    /**
     * 将制定路径下的类转换为url
     *
     * @return URL:file:/Users/gaoyunfan/code/graduate/target/classes/com/gyf/test
     */
    public static URL[] getMyURLs() {
        URL url = null;
        try {
            url = new File(projectClassPath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new URL[]{url};
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        System.out.println("load class " + name);
        Class clazz = null;
        clazz = findLoadedClass(name);
        //如果已经被加载过了
        if (clazz != null) {
            if (resolve) {
                //负责完成Class对象的链接
                resolveClass(clazz);
            }
            //如果是需要热加载的类并且被修改过了，则重新加载
            if (isHotSwapClass(clazz) && isModify(name)) {
                hcl = new HotSwapClassLoader();
                clazz = customLoad(name, hcl);
            }
            return clazz;
        }
        try {
            ClassLoader system = ClassLoader.getSystemClassLoader();
            clazz = system.loadClass(name);
            if (clazz != null) {
                if (resolve) {
                    //负责完成Class对象的链接
                    resolveClass(clazz);
                }
                if (!isHotSwapClass(clazz)) {
                    return clazz;
                }
            }
        } catch (ClassNotFoundException e) {

        }

        //既不是java包也不是已经加载过的，直接加载
        return customLoad(name, this);
    }


    /**
     * 自定义加载
     *
     * @param name
     * @param hcl
     * @return
     */
    private Class customLoad(String name, ClassLoader hcl) throws ClassNotFoundException {
        return customLoad(name, false, hcl);
    }

    /**
     * 自定义加载
     * 加载完成后记录这次加载的时间
     *
     * @param name    类名
     * @param resolve 是否链接
     * @param hcl     使用的类加载器
     * @return
     */
    private Class customLoad(String name, boolean resolve, ClassLoader hcl) throws ClassNotFoundException {
        int i;

        Class clazz = ((HotSwapClassLoader) hcl).findClass(name);
        if (resolve) {
            ((HotSwapClassLoader) hcl).resolveClass(clazz);
        }
        long lastModifyTime = getClassLastModifyTime(name);
        cacheLastModifyTimeMap.put(name, lastModifyTime);
        return clazz;
    }

    /**
     * @param name
     * @return 文件上次修改的时间
     */
    private long getClassLastModifyTime(String name) {
        String path = getClassCompletePath(name);
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException(new FileNotFoundException(path));
        }
        return file.lastModified();
    }

    private String getClassCompletePath(String name) {
//        String simpleName = name.substring(name.lastIndexOf(".") + 1);
        String simpleName = name.replaceAll("\\.", "/");
        return projectClassPath + packagePath + simpleName + ".class";
    }

    /**
     * @param name 文件名
     * @return 文件是否被修改
     */
    private boolean isModify(String name) {
        Long lastModify = getClassLastModifyTime(name);
        long previousModifyTime = cacheLastModifyTimeMap.get(name);
        return lastModify > previousModifyTime;
    }

    /**
     * 指向重载的方法
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    public Class load(String name) throws ClassNotFoundException {
        return loadClass(name);
    }

    /**
     * 指向重载的方法
     *
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    private boolean isHotSwapClass(Class aClass) {
        return aClass.isAnnotationPresent(HotSwap.class);
    }
}
