package com.cctang.cloud.controller;

import com.cctang.cloud.service.CarService;
import com.cctang.cloud.service.UserService;
import com.cctang.springcloud.entities.Car;
import com.cctang.springcloud.entities.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/18 21:22
 * @description
 */
@RestController
public class Transaction {
    @Resource
    private UserService userService;

    @Resource
    private CarService carService;

    @RequestMapping(value = "/sent")
    @Transactional
    public void sent(){
        addUser();
        addCar();
    }

    @Transactional
    public void addUser(){
        User us = new User();
        us.setName("高");
        us.setSex("男");
        int resultUser = userService.addUser(us);

        if(resultUser > 0){
            System.out.println("用户{}插入成功"+us.getId());
        }else {
            System.out.println("用户插入失败");
        }
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addCar(){
        
        Car car = new Car();
        car.setType("宝");
        int resultCar = carService.addCar(car);
        System.out.println(1/0);
        if(resultCar > 0){
            System.out.println("汽车{}插入成功"+car.getId());
        }else {
            System.out.println("汽车插入失败");
        }

    }
}
