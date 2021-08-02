package com.cctang.designModle.factory.factoryMethod;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 0:43
 * @description 外界通过调用具体工厂类的方法，从而创建不同具体产品类的实例
 */
public class Client {
    public static void main(String[] args) {
        //客户要产品A
        FactoryA mFactoryA = new FactoryA();
        mFactoryA.makeProduct().Show();

        //客户要产品A
        FactoryB mFactoryB = new FactoryB();
        mFactoryB.makeProduct().Show();
    }
}
