package com.cctang.designModle.factory.simpleFactory;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 23:32
 * @description
 */
public class AsusComputer extends IComputer {
    @Override
    public void start() {
        System.out.println("华硕电脑启动");
    }
}
