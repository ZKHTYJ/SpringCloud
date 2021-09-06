package com.cctang.designModle.transaction.controller;

import com.cctang.designModle.transaction.service.CarService;
import com.cctang.designModle.transaction.service.UserService;
import com.cctang.springcloud.entities.Car;
import com.cctang.springcloud.entities.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/12 22:41
 * @description
 */
@Component
public class Transaction {
    @Resource
   private UserService userService;
    @Resource
   private CarService carService;
    public void sent(){
        User us = new User();
        us.setName("王强");
        us.setSex("男");
        Car car = new Car();
        car.setType("奔驰");

        int resultUser = userService.addUser(us);
        int resultCar = carService.addCar(car);
        if(resultUser > 0){
            System.out.println("用户{}插入成功"+us.getId());
        }else {
            System.out.println("用户插入失败");
        }

        if(resultCar > 0){
            System.out.println("汽车{}插入成功"+car.getId());
        }else {
            System.out.println("汽车插入失败");
        }


    }
    public static void main(String[] args) {
        Transaction tt = new Transaction();
        tt.sent();

    }
}
