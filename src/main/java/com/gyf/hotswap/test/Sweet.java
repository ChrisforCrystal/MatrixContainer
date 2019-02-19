package com.gyf.hotswap.test;

import com.gyf.hotswap.annotation.HotSwap;
import com.gyf.hotswap.interfacer.HotChangeItem;

/**
 * @author yunfan.gyf
 **/
@HotSwap
public class Sweet implements HotChangeItem {
    @Override
    public void sweet() {
        System.out.println("I'm sweet version 2, loaded by "+this.getClass().getClassLoader());
    }


    public static void main(String[] args) {

    }
}
