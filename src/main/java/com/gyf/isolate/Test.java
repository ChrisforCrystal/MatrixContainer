package com.gyf.isolate;

import com.gyf.MatrixContainer;
import com.netease.sofaservice.MyJar1Service;
import com.netease.sofaservice.MyJar2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author yunfan.gyf
 **/
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args)  {
        MatrixContainer.start(Test.class, "main");
        MyJar1Service myJar1Service = new MyJar1Service();
        myJar1Service.show();
        MyJar2Service myJar2Service = new MyJar2Service();
        myJar2Service.show();
    }
}
