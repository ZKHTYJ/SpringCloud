package com.cctang.designModle.factory.factoryMethod;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 0:37
 * @description 创建具体产品类（继承抽象产品类）， 定义生产的具体产品；
 */
public class ProductA extends IProduct{
    @Override
    public void Show() {
        System.out.println("生产出了产品A");
    }
}
