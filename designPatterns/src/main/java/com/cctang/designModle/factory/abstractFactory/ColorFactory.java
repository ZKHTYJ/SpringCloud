package com.cctang.designModle.factory.abstractFactory;

import com.cctang.designModle.factory.abstractFactory.impl.Blue;
import com.cctang.designModle.factory.abstractFactory.impl.Green;
import com.cctang.designModle.factory.abstractFactory.impl.Red;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 1:03
 * @description 创建扩展了 AbstractFactory 的工厂类，基于给定的信息生成实体类的对象。
 */
public class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String color) { if(color == null){
        return null;
    }
        if(color.equalsIgnoreCase("RED")){
            return new Red();
        } else if(color.equalsIgnoreCase("GREEN")){
            return new Green();
        } else if(color.equalsIgnoreCase("BLUE")){
            return new Blue();
        }

        return null;
    }

    @Override
    public Shape getShape(String shape) {
        return null;
    }
}
