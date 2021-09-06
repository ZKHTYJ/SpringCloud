package com.cctang.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cctang.springcloud.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/8/30 22:24
 * @description
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
