package com.cctang.designModle.singleton;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 16:21
 * @description 静态内部类
 *
 *
 * 优点
 *     资源利用率高，不执行getInstance()不被实例，可以执行该类其他静态方法
 * 缺点
 *     第一次加载时反应不够快
 */
public class StaticInnerClass {
    private StaticInnerClass() {
    }

    private static class SingletonHelp {
        static StaticInnerClass instance = new StaticInnerClass();
    }

    public static StaticInnerClass getInstance() {
        return SingletonHelp.instance;
    }
}
