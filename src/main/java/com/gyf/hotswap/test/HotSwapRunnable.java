package com.gyf.hotswap.test;

import com.gyf.hotswap.HotSwapManager;
import com.gyf.hotswap.interfacer.HotChangeItem;

/**
 * @author yunfan.gyf
 **/
public class HotSwapRunnable implements Runnable {
    private HotChangeItem sweet;
    @Override
    public void run() {
        try {
            while (true) {
                sweet = HotSwapManager.getBean(Sweet.class);
                sweet.sweet();
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
