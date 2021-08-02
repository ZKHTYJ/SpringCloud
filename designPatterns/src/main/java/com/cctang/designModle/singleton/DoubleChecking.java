package com.cctang.designModle.singleton;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 16:18
 * @description 双重检测
 *
 * 优点
 *     资源利用率高，不执行getInstance()就不被实例，可以执行该类其他静态方法
 * 缺点
 *     第一次加载时反应不快，由于java内存模型一些原因偶尔失败
 */
public class DoubleChecking {
    private DoubleChecking() {
    }
    public static DoubleChecking instance = null;

    public static DoubleChecking getInstance() {
        if (instance == null) {
            synchronized (DoubleChecking.class) {
                if (instance == null) {
                    instance = new DoubleChecking();
                }
            }
        }
        return instance;
    }
}
