package com.cctang.designModle.builder;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 14:53
 * @description 套餐B 具体建造者
 */
public class SubMealBuilderB extends MealBuilder{
    @Override
    public void buildFood() {
        meal.setFood("鸡肉卷");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("果汁");
    }
}
