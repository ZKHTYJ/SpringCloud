package com.cctang.designModle.factory.abstractFactory;

import com.cctang.designModle.factory.abstractFactory.impl.Circle;
import com.cctang.designModle.factory.abstractFactory.impl.Rectangle;
import com.cctang.designModle.factory.abstractFactory.impl.Square;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/3 1:01
 * @description 创建扩展了 AbstractFactory 的工厂类，基于给定的信息生成实体类的对象。
 */
public class ShapeFactory extends AbstractFactory{
    @Override
    public Color getColor(String color) {
        return null;
    }

    @Override
    public Shape getShape(String shapeType) {
        if(shapeType == null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("CIRCLE")){
            return new Circle();
        } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
            return new Rectangle();
        } else if(shapeType.equalsIgnoreCase("SQUARE")){
            return new Square();
        }
        return null;
    }
}
