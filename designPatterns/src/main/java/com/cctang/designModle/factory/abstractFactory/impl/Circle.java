package com.cctang.designModle.factory.abstractFactory.impl;

import com.cctang.designModle.factory.abstractFactory.Shape;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 0:55
 * @description 创建实现接口的实体类。
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("创建一个圆形⚪");
    }
}
