package com.gyf.hotswap.test;

import com.gyf.hotswap.annotation.HotSwap;

/**
 * @author yunfan.gyf
 **/
@HotSwap
public class Bitter {
    public void bitter() {
        System.out.println("I'm bitter version 3, loaded by "+this.getClass().getClassLoader());
    }


    public static void main(String[] args) {

    }
}
