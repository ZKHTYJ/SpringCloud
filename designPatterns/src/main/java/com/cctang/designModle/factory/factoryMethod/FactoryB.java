package com.cctang.designModle.factory.factoryMethod;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 0:42
 * @description 创建具体工厂类（继承抽象工厂类），定义创建对应具体产品实例的方法；
 */
public class FactoryB extends  IFactory{
    @Override
    public IProduct makeProduct() {
        return new ProductB();
    }
}
