package com.cctang.cloud.dao;

import com.cctang.springcloud.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/18 21:24
 * @description
 */
@Mapper
public interface UserDao {
    @Insert("insert into user(name, sex) values(#{name},#{sex})")
    public int addUser(User user);
}
