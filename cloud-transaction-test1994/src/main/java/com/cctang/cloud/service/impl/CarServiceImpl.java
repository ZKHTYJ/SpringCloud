package com.cctang.cloud.service.impl;

import com.cctang.cloud.dao.CarDao;
import com.cctang.cloud.service.CarService;
import com.cctang.springcloud.entities.Car;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/18 21:28
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
