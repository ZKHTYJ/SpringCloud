package com.cctang.designModle.factory.simpleFactory;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 23:35
 * @description
 */
public class Client {
    public static void main(String[] args) {
        ComputerFactory.createComputer("lenovo").start();
    }
}
