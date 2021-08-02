package com.cctang.designModle.factory.simpleFactory;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 23:31
 * @description
 */
public class LenovoComputer extends IComputer{
    @Override
    public void start() {
        System.out.println("联想电脑启动");
    }
}
