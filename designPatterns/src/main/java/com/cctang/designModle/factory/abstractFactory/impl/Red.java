package com.cctang.designModle.factory.abstractFactory.impl;

import com.cctang.designModle.factory.abstractFactory.Color;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 0:59
 * @description 创建实现接口的实体类。
 */
public class Red implements Color {
    @Override
    public void fill() {
        System.out.println("填充红色");
    }
}
