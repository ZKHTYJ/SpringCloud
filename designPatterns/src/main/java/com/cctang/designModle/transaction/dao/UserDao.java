package com.cctang.designModle.transaction.dao;

import com.cctang.springcloud.entities.User;
import org.apache.ibatis.annotations.Insert;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/12 22:35
 * @description
 */
public interface UserDao {
    @Insert("insert into user(name, sex) values(#{name},#{sex})")
    public int addUser(User user);
}
