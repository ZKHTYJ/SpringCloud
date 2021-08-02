package com.cctang.designModle.builder;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 14:51
 * @description 套餐A  具体建造者
 */
public class SubMealBuilderA extends MealBuilder{

    @Override
    public void buildFood() {
        meal.setFood("汉堡");
    }

    @Override
    public void buildDrink() {
        meal.setDrink("可乐");
    }
}
