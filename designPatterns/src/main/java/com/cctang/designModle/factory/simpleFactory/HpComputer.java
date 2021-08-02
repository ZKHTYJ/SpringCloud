package com.cctang.designModle.factory.simpleFactory;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 23:31
 * @description
 */
public class HpComputer extends IComputer{
    @Override
    public void start() {
        System.out.println("惠普电脑启动");
    }
}
