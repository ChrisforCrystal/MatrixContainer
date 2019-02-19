package com.gyf.hotswap.test;

import com.gyf.hotswap.annotation.HotSwap;
import com.gyf.hotswap.interfacer.HotChangeItem;

/**
 * @author yunfan.gyf
 **/
public class Bitter implements HotChangeItem {
    @Override
    public void sweet() {
        System.out.println("I'm bitter version 6, loaded by "+this.getClass().getClassLoader());
    }


    public static void main(String[] args) {

    }
}
