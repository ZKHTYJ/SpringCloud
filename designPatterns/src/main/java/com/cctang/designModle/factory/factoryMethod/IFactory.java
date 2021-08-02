package com.cctang.designModle.factory.factoryMethod;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 0:36
 * @description 创建抽象工厂类，定义具体工厂的公共接口
 */
public abstract class IFactory {
    public abstract IProduct makeProduct();
}
