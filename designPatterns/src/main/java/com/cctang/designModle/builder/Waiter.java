package com.cctang.designModle.builder;

import com.cctang.springcloud.entities.Meal;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 14:55
 * @description 服务员 指挥者
 */
public class Waiter {
    private MealBuilder mealBuilder;

    public Waiter(MealBuilder mealBuilder){
        this.mealBuilder = mealBuilder;
    }
    public Meal construct(){
        //准备食物
        mealBuilder.buildFood();
        //准备饮料
        mealBuilder.buildDrink();

        //准备完毕，返回一个完整的套餐给客户
        return mealBuilder.getMeal();
    }

}
