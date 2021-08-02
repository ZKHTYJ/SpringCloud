package com.cctang.designModle.builder;

import com.cctang.springcloud.entities.Meal;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/2 14:57
 * @description 取餐  客户调用类。
 */
public class Client {
    public static void main(String[] args) {
        // 套餐A
        SubMealBuilderA a = new SubMealBuilderA();
        // 套餐B
        SubMealBuilderB b = new SubMealBuilderB();

        //准备套餐A的服务员
        Waiter waiterA = new Waiter(a);

        //准备套餐B的服务员
        Waiter waiterB = new Waiter(b);

        //获得套餐A
        Meal mealA = waiterA.construct();
        System.out.print("套餐A的组成部分:");
        System.out.println("主食："+mealA.getFood()+"；   "+"饮料："+mealA.getDrink());

        //获得套餐B
        Meal mealB = waiterB.construct();
        System.out.print("套餐B的组成部分:");
        System.out.println("主食："+mealB.getFood()+"；   "+"饮料："+mealB.getDrink());

    }
}
