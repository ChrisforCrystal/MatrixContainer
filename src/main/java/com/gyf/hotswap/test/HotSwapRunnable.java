package com.gyf.hotswap.test;

import com.gyf.hotswap.HotSwapManager;
import com.gyf.hotswap.interfacer.HotChangeItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yunfan.gyf
 **/
public class HotSwapRunnable implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(HotSwapRunnable.class);
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
