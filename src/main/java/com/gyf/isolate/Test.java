package com.gyf.isolate;

import com.gyf.MatrixContainer;
import com.gyf.isolate.classloader.ContainerClassLoader;
import com.netease.sofaservice.MyJar1Service;
import com.netease.sofaservice.MyJar2Service;

import java.net.MalformedURLException;

/**
 * @author yunfan.gyf
 **/
public class Test {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException {
        MatrixContainer.start("com.gyf.isolate.Test", "main");
//        ContainerClassLoader cl = new ContainerClassLoader("/Users/gaoyunfan/code/graduate/target/classes");
//        Test a = (Test)cl.loadClass("com.gyf.isolate.Test").newInstance();
        new MyJar1Service().show();
        new MyJar2Service().show();
    }
}
