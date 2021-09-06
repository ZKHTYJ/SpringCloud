package com.cctang.designModle.transaction.service.impl;

import com.cctang.designModle.transaction.dao.CarDao;
import com.cctang.designModle.transaction.service.CarService;
import com.cctang.springcloud.entities.Car;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/12 22:44
 * @description
 */
@Service
public class CarServiceImpl implements CarService {
    @Resource
    private CarDao carDao;
    @Override
    public int addCar(Car car) {
        return carDao.addCar(car);
    }
}
