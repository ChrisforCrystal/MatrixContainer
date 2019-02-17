package com.gyf.hotswap.test;

/**
 * @author yunfan.gyf
 **/
public class HotSwapTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new HotSwapRunnable());
        thread.start();
    }


}

