package com.cctang.designModle.builder;


import com.cctang.designModle.common.Meal;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 14:45
 * @description  建造者模式可以用于描述KFC如何创建套餐：
 * 套餐是一个复杂对象，它一般包含主食（如汉堡、鸡肉卷等）和饮料（如果汁、可乐等）等组成部分，
 * 不同的套餐有不同的组成部分，而KFC的服务员可以根据顾客的要求，一步一步装配这些组成部分，构造一份完整的套餐，然后返回给顾客。
 *
 *  抽象建造者
 */
public abstract class MealBuilder {
    Meal meal = new Meal();
    public abstract void buildFood();

    public abstract void buildDrink();

    public Meal getMeal(){
        return meal;
    }
}
