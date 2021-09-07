package com.cctang.springcloud.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cctang.springcloud.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author cctang
 * @version 1.0
 * @date 2021/9/7 21:04
 * @description
 */
@SpringBootTest
public class WrapperTest {
    @Resource
    private UserMapper userMapper;

    // 查询姓名和邮箱都不为空的用户并且年龄大于等于12
    @Test
    public void contextLoads(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    // 查询名字为cc的用户
    @Test
    public void queryOne(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","gg");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }
    // 查询年龄在10到20岁之间的用户
    @Test
    public void queryOne1(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",10,20);
        userMapper.selectList(wrapper).forEach(System.out::println);
        System.out.println(userMapper.selectCount(wrapper));

    }

    // 模糊查询
    @Test
    public void queryOne2(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notLike("name","a")
                .likeRight("email","t");
        userMapper.selectMaps(wrapper).forEach(System.out::println);
    }

    // 模糊查询二 查询id<3的用户
    @Test
    public void queryOne3(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.inSql("id","select id from user where id<3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    @Test
    public void queryOne4(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notInSql("id","select id from user where id>3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    // 通过id进行排序排序
    @Test
    public void queryOne5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

}
