package com.gyf.hotswap.test;

import com.gyf.MatrixContainer;
import com.gyf.hotswap.annotation.HotSwap;

/**
 * @author yunfan.gyf
 **/
@HotSwap
public class HotSwapTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new HotSwapRunnable());
        thread.start();
    }


}

