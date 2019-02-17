package com.gyf.hotswap.test;

/**
 * @author yunfan.gyf
 **/
public class Sweet {
    public void sweet() {
        System.out.println("I'm sweet version 1, loaded by "+this.getClass().getClassLoader());
    }

    public static void main(String[] args) {

    }
}
