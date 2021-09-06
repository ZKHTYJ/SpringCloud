package com.cctang.designModle.transaction.dao;

import com.cctang.springcloud.entities.Car;
import com.cctang.springcloud.entities.User;
import org.apache.ibatis.annotations.Insert;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/12 22:42
 * @description
 */
public interface CarDao {
    @Insert("insert into car(type) values(#{type})")
    public int addCar(Car car);
}
