package com.cctang.designModle.singleton;


/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 15:55
 * @description 饿汉模式
 *
 * 优点
 *     1.线程安全
 *     2.在类加载的同时已经创建好一个静态对象，调用时反应速度快
 * 缺点
 *     资源效率不高，可能getInstance()永远不会执行到，但执行该类的其他静态方法或者加载了该类（class.forName)，那么这个实例仍然初始化
 */
public class Hungry {
    private final static Hungry INSTANCE = new Hungry();
    private Hungry(){}

    public static Hungry getInstance(){
        return INSTANCE;
    }
}
