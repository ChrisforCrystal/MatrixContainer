
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * 只要功能是重新加载更改过的.class 文件，达到热替换的作用
 *
 * @author banana
 */
public class HotSwapURLClassLoader extends URLClassLoader {

    // 缓存加载 class 文件的最后最新修改时间
    public static Map<String, Long> cacheLastModifyTimeMap = new HashMap<String, Long>();
    // 工程 class 类所在的路径
    public static String projectClassPath = "/Users/gaoyunfan/code/graduate/target/classes";
    // 所有的测试的类都在同一个包下
    public static String packagePath = "/";

    private static HotSwapURLClassLoader hcl = new HotSwapURLClassLoader();

    public HotSwapURLClassLoader() {
        // 设置 ClassLoader 加载的路径
        super(getMyURLs());
    }

    public static HotSwapURLClassLoader getClassLoader() {
        return hcl;
    }

    private static URL[] getMyURLs() {
        URL url = null;
        try {
            url = new File(projectClassPath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new URL[]{url};
    }

    /**
     * 重写 loadClass，不采用双亲委托机制 ("java." 开头的类还是会由系统默认 ClassLoader 加载)
     */
    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clazz = null;
        // 查看 HotSwapURLClassLoader 实例缓存下，是否已经加载过 class
        // 不同的 HotSwapURLClassLoader 实例是不共享缓存的
        clazz = findLoadedClass(name);
        if (clazz != null) {
            if (resolve) {
                resolveClass(clazz);
            }
            // 如果 class 类被修改过，则重新加载
            if (isModify(name)) {
                hcl = new HotSwapURLClassLoader();
                clazz = customLoad(name, hcl);
            }
            return (clazz);
        }

        // 如果类的包名为 "java." 开始，则有系统默认加载器 AppClassLoader 加载
        if (name.startsWith("java.")) {
            try {
                // 得到系统默认的加载 cl，即 AppClassLoader
                ClassLoader system = ClassLoader.getSystemClassLoader();
                clazz = system.loadClass(name);
                if (clazz != null) {
                    if (resolve) {
                        resolveClass(clazz);
                    }
                    return (clazz);
                }
            } catch (ClassNotFoundException e) {
                // Ignore
            }
        }

        return customLoad(name, this);
    }

    public Class load(String name) throws Exception {
        return loadClass(name);
    }

    /**
     * 自定义加载
     *
     * @param name
     * @param cl
     * @return
     * @throws ClassNotFoundException
     */
    public Class customLoad(String name, ClassLoader cl) throws ClassNotFoundException {
        return customLoad(name, false, cl);
    }

    /**
     * 自定义加载
     *
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    public Class customLoad(String name, boolean resolve, ClassLoader cl) throws ClassNotFoundException {
        //findClass() 调用的是 URLClassLoader 里面重载了 ClassLoader 的 findClass() 方法
        Class clazz = ((HotSwapURLClassLoader) cl).findClass(name);
        if (resolve) {
            ((HotSwapURLClassLoader) cl).resolveClass(clazz);
        }
        // 缓存加载 class 文件的最后修改时间
        long lastModifyTime = getClassLastModifyTime(name);
        cacheLastModifyTimeMap.put(name, lastModifyTime);
        return clazz;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return loadClass(name, false);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // TODO Auto-generated method stub
        return super.findClass(name);
    }

    /**
     * @param name
     * @return .class 文件最新的修改时间
     */
    private long getClassLastModifyTime(String name) {
        String path = getClassCompletePath(name);
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException(new FileNotFoundException(name));
        }
        return file.lastModified();
    }

    /**
     * 判断这个文件跟上次比是否修改过
     *
     * @param name
     * @return
     */
    private boolean isModify(String name) {
        long lastmodify = getClassLastModifyTime(name);
        long previousModifyTime = cacheLastModifyTimeMap.get(name);
        if (lastmodify > previousModifyTime) {
            return true;
        }
        return false;
    }

    /**
     * @param name
     * @return .class 文件的完整路径 (e.g. E:/A.class)
     */
    private String getClassCompletePath(String name) {
        String simpleName = name.substring(name.lastIndexOf(".") + 1);
        return projectClassPath + packagePath + simpleName + ".class";
    }

    public static void main(String[] args) {

    }

}
