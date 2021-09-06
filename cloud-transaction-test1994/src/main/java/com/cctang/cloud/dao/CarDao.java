package com.cctang.cloud.dao;

import com.cctang.springcloud.entities.Car;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/18 21:24
 * @description
 */
@Mapper
public interface CarDao {
    @Insert("insert into car(type) values(#{type})")
    public int addCar(Car car);
}
