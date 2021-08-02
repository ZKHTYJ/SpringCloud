package com.cctang.designModle.factory.abstractFactory.impl;

import com.cctang.designModle.factory.abstractFactory.Color;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 1:04
 * @description
 */
public class Green implements Color {
    @Override
    public void fill() {
        System.out.println("填充绿色");
    }
}
