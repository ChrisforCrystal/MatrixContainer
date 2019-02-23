package com.gyf.hotswap.test;

import com.gyf.hotswap.HotSwapManager;
import com.gyf.hotswap.interfacer.HotChangeItem;
import org.apache.log4j.Logger;

/**
 * @author yunfan.gyf
 **/
public class HotSwapRunnable implements Runnable {
    private static Logger logger = Logger.getLogger(HotSwapRunnable.class);
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
