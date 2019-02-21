package com.gyf.isolate;

import bundles.bundle1.classpath.A;
import com.gyf.MatrixContainer;
import com.gyf.isolate.classloader.ContainerClassLoader;

import java.net.MalformedURLException;

/**
 * @author yunfan.gyf
 **/
public class Test {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException {
        MatrixContainer.start("com.gyf.isolate.Test", "main");
//        ContainerClassLoader cl = new ContainerClassLoader("/Users/gaoyunfan/code/graduate/target/classes");
//        Test a = (Test)cl.loadClass("com.gyf.isolate.Test").newInstance();
        System.out.println(Test.class.getClassLoader());
        Thread.sleep(10000);
    }
}
