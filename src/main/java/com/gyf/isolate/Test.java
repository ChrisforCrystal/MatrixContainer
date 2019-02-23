package com.gyf.isolate;

import com.gyf.MatrixContainer;
import com.netease.sofaservice.MyJar1Service;
import com.netease.sofaservice.MyJar2Service;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.net.MalformedURLException;

/**
 * @author yunfan.gyf
 **/
public class Test {

    private static Logger logger = Logger.getLogger(Test.class);

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, InterruptedException {
//        MatrixContainer.start("com.gyf.isolate.Test", "main");
        MyJar1Service myJar1Service = new MyJar1Service();
        myJar1Service.show();
        MyJar2Service myJar2Service = new MyJar2Service();
        myJar2Service.show();
    }
}
